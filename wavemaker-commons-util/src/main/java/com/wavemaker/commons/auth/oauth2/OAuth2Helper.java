package com.wavemaker.commons.auth.oauth2;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.auth.oauth2.extractors.AccessTokenRequestContext;
import com.wavemaker.commons.auth.oauth2.extractors.AccessTokenResponseExtractor;
import com.wavemaker.commons.io.ClassPathFile;
import com.wavemaker.commons.json.JSONUtils;

/**
 * Created by srujant on 26/7/17.
 */
public class OAuth2Helper {

    private static final String OAUTH_CALLBACK_URL_RESPONSE = "templates/oauth2/oauth_callback_url_response.ftl";
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Helper.class);

    private static AccessTokenResponseExtractor accessTokenResponseExtractor = new AccessTokenResponseExtractor();

    private OAuth2Helper() {
    }

    public static String getAuthorizationUrl(OAuth2ProviderConfig oAuth2ProviderConfig, String redirectUrl, String state) {
        String scope = getScopeValue(oAuth2ProviderConfig);
        String encodedState = new String(Base64.getEncoder().encode(state.getBytes()));
        StringBuilder sb = new StringBuilder(oAuth2ProviderConfig.getAuthorizationUrl()).append("?")
                .append(OAuth2Constants.CLIENT_ID).append("=").append(oAuth2ProviderConfig.getClientId())
                .append("&").append(OAuth2Constants.REDIRECT_URI).append("=").append(redirectUrl)
                .append("&").append(OAuth2Constants.RESPONSE_TYPE).append("=code")
                .append("&").append(OAuth2Constants.STATE).append("=").append(encodedState);
        if (StringUtils.isNotBlank(scope)) {
            sb.append("&").append(OAuth2Constants.SCOPE).append("=").append(scope);
        }
        return sb.toString();
    }

    public static Map<String, String> getStateObject(String stateParameter) {
        String stateParam = new String(Base64.getDecoder().decode(stateParameter));
        try {
            return JSONUtils.toObject(stateParam, Map.class);
        } catch (IOException e) {
            throw new WMRuntimeException(e);
        }
    }


    public static String getStateParameterValue(Map<String, String> stateObject) {
        String stateParameter = JSONUtils.toJSON(stateObject);
        return new String(Base64.getEncoder().encode(stateParameter.getBytes()));
    }


    public static String getAccessTokenApiRequestBody(OAuth2ProviderConfig oAuth2ProviderConfig, String code, String redirectUri) {
        return new StringBuilder(OAuth2Constants.CLIENT_ID).append("=").append(oAuth2ProviderConfig.getClientId())
                .append("&").append(OAuth2Constants.CLIENT_SECRET).append("=").append(oAuth2ProviderConfig.getClientSecret())
                .append("&").append(OAuth2Constants.CODE).append("=").append(code)
                .append("&").append(OAuth2Constants.REDIRECT_URI).append("=").append(redirectUri)
                .append("&").append(OAuth2Constants.GRANT_TYPE).append("=authorization_code").toString();
    }

    public static String extractAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        logger.debug("AuthorizationServerResponse for get token api is {}", accessTokenRequestContext.getResponseBody());
        String accessToken = accessTokenResponseExtractor.getAccessToken(accessTokenRequestContext);
        if (accessToken == null) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.AccessTokenResponseExtractor.not.found"), accessTokenRequestContext.getMediaType());
        }
        return accessToken;
    }

    public static String getCallbackResponse(String providerId, String accessToken, String customUrlScheme, String requestSourceType) {
        Map<String, Object> input = new HashMap<>();
        input.put("providerId", providerId);
        input.put(OAuth2Constants.CUSTOM_URL_SCHEME, customUrlScheme);
        input.put(OAuth2Constants.REQUEST_SOURCE_TYPE, requestSourceType);
        input.put("accessToken", accessToken);

        StrSubstitutor strSubstitutor = new StrSubstitutor(input);
        return strSubstitutor.replace(new ClassPathFile(OAUTH_CALLBACK_URL_RESPONSE).getContent().asString());
    }

    private static String getScopeValue(OAuth2ProviderConfig oAuth2ProviderConfig) {
        List<Scope> scopesList = oAuth2ProviderConfig.getScopes();
        return scopesList.stream().map(Scope::getValue).distinct().collect(Collectors.joining(" "));
    }
}
