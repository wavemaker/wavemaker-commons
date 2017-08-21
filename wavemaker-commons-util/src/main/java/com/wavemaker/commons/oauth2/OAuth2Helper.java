package com.wavemaker.commons.oauth2;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.ResourceNotFoundException;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.io.ClassPathFile;

/**
 * x
 * Created by srujant on 26/7/17.
 */
public class OAuth2Helper {

    private static final String OAUTH_CALLBACK_URL_RESPONSE = "templates/oauth2/oauth_callback_url_response.ftl";

    private static final Logger logger = LoggerFactory.getLogger(OAuth2Helper.class);

    public static String getAuthorizationUrl(OAuth2ProviderConfig oAuth2ProviderConfig, String redirectUrl, JSONObject stateObject) {
        String scope = getScopeValue(oAuth2ProviderConfig);
        String encodedState = new String(Base64.getEncoder().encode(stateObject.toString().getBytes()));
        StringBuilder sb = new StringBuilder(oAuth2ProviderConfig.getAuthorizationUrl()).append("?client_id=")
                .append(oAuth2ProviderConfig.getClientId())
                .append("&redirect_uri=").append(redirectUrl)
                .append("&response_type=code")
                .append("&state=").append(encodedState);
        if (StringUtils.isNotBlank(scope)) {
            sb.append("&scope=").append(scope);
        }
        return sb.toString();
    }

    public static JSONObject getStateObject(String stateParameter) {
        try {
            String stateParam = new String(Base64.getDecoder().decode(stateParameter));
            JSONTokener jsonTokener = new JSONTokener(stateParam);
            return new JSONObject(jsonTokener);
        } catch (JSONException e) {
            throw new WMRuntimeException("Failed to read state parameter", e);
        }
    }

    public static String getAccessTokenApiRequestBody(OAuth2ProviderConfig oAuth2ProviderConfig, String providerId, String code, String redirectUri) {
        if (oAuth2ProviderConfig == null) {
            throw new ResourceNotFoundException("No AuthProviders configured for provider with providerId " + providerId);
        }
        String requestBody = new StringBuilder("client_id=").append(oAuth2ProviderConfig.getClientId())
                .append("&client_secret=").append(oAuth2ProviderConfig.getClientSecret())
                .append("&code=").append(code)
                .append("&redirect_uri=").append(redirectUri)
                .append("&grant_type=authorization_code").toString();

        return requestBody;
    }

    public static String extractAccessToken(String accessTokenApiResponseBody) throws IOException, JSONException {
        logger.debug("AuthorizationServerResponse for get token api is {}", accessTokenApiResponseBody);
        JSONTokener jsonTokener = new JSONTokener(accessTokenApiResponseBody);
        JSONObject accesTokenObject = new JSONObject(jsonTokener);
        return (String) accesTokenObject.get("access_token");
    }

    public static String getCallbackResponse(String providerId, String accessToken) throws IOException, JSONException {
        Map<String, Object> input = new HashMap<>();
        input.put("providerId", providerId);
        input.put("accessToken", accessToken);
        StrSubstitutor strSubstitutor = new StrSubstitutor(input);
        return strSubstitutor.replace(new ClassPathFile(OAUTH_CALLBACK_URL_RESPONSE).getContent().asString());
    }

    private static String getScopeValue(OAuth2ProviderConfig oAuth2ProviderConfig) {
        List<Scope> scopesList = oAuth2ProviderConfig.getScopes();
        return scopesList.stream().map(scope -> scope.getValue()).distinct().collect(Collectors.joining(" "));
    }
}
