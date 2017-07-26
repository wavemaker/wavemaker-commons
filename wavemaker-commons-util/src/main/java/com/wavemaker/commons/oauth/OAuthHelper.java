package com.wavemaker.commons.oauth;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
 * Created by srujant on 26/7/17.
 */
public class OAuthHelper {

    private static final String OAUTH_CALLBACK_URL_RESPONSE = "templates/oauth/oauth_callback_url_response.ftl";

    private static final Logger logger = LoggerFactory.getLogger(OAuthHelper.class);

    public static String getAuthorizationUrl(OAuthProviderConfig oAuthProviderConfig, String redirectUrl, String scope, JSONObject stateObject) {
        String encodedstate = new String(Base64.getEncoder().encode(stateObject.toString().getBytes()));
        return new StringBuilder(oAuthProviderConfig.getAuthorizationUrl()).append("?client_id=")
                .append(oAuthProviderConfig.getClientId())
                .append("&redirect_uri=").append(redirectUrl)
                .append("&scope=").append(scope)
                .append("&response_type=code")
                .append("&prompt=consent")
                .append("&state=").append(encodedstate).toString();
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

    public static String getAccessTokenApiRequestBody(OAuthProviderConfig oAuthProviderConfig, String providerId, String code, String redirectUri) {
        if (oAuthProviderConfig == null) {
            throw new ResourceNotFoundException("No AuthProviders configured for provider with providerId " + providerId);
        }
        String requestBody = new StringBuilder("client_id=").append(oAuthProviderConfig.getClientId())
                .append("&client_secret=").append(oAuthProviderConfig.getClientSecret())
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
}
