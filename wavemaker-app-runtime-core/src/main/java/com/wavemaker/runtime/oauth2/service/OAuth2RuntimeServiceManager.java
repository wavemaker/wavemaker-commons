package com.wavemaker.runtime.oauth2.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.http.entity.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.ResourceNotFoundException;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.oauth2.OAuth2Helper;
import com.wavemaker.commons.oauth2.OAuth2ProviderConfig;
import com.wavemaker.commons.oauth2.extractors.AccessTokenRequestContext;
import com.wavemaker.commons.util.HttpRequestUtils;
import com.wavemaker.runtime.RuntimeEnvironment;
import com.wavemaker.runtime.WMObjectMapper;
import com.wavemaker.runtime.rest.builder.HttpRequestDetailsBuilder;
import com.wavemaker.runtime.rest.model.HttpRequestDetails;
import com.wavemaker.runtime.rest.model.HttpResponseDetails;
import com.wavemaker.runtime.rest.service.RestConnector;

/**
 * Created by srujant on 18/7/17.
 */
public class OAuth2RuntimeServiceManager {

    private static RestConnector restConnector = new RestConnector();
    private static final String REDIRECT_URL = "/services/oauth2/${providerId}/callback";

    private List<OAuth2ProviderConfig> oAuth2ProviderConfigList = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(OAuth2RuntimeServiceManager.class);

    @PostConstruct
    public void init() {
        InputStream oauthProvidersJsonFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("oauth-providers.json");
        if (oauthProvidersJsonFile != null) {
            try {
                this.oAuth2ProviderConfigList = WMObjectMapper.getInstance().readValue(oauthProvidersJsonFile, new TypeReference<List<OAuth2ProviderConfig>>() {
                });
            } catch (IOException e) {
                throw new WMRuntimeException(e);
            }

        }
    }


    public String getAuthorizationUrl(String providerId, HttpServletRequest httpServletRequest) {
        OAuth2ProviderConfig oAuth2ProviderConfig = getOAuthProviderConfig(providerId);

        String baseUrl = HttpRequestUtils.getBaseUrl(httpServletRequest);
        String appPath = new StringBuilder(baseUrl).append(httpServletRequest.getContextPath()).toString();
        String redirectUrl = getRedirectUrl(providerId, httpServletRequest, baseUrl, appPath);
        try {
            JSONObject stateObject = new JSONObject();
            stateObject.put("mode", "runtTime");
            stateObject.put("appPath", appPath);
            return OAuth2Helper.getAuthorizationUrl(oAuth2ProviderConfig, redirectUrl, stateObject);
        } catch (JSONException e) {
            throw new InvalidInputException("Invalid input to jsonObject", e);
        }
    }

    private String getRedirectUrl(String providerId, HttpServletRequest httpServletRequest, String baseUrl, String appPath) {
        String redirectUrl;
        String studioUrl = RuntimeEnvironment.getStudioUrl();
        if (StringUtils.isNotBlank(studioUrl)) {
            redirectUrl = studioUrl + REDIRECT_URL;
        } else {
            redirectUrl = new StringBuilder(appPath).append(REDIRECT_URL).toString();
        }
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("providerId", providerId);
        redirectUrl = StrSubstitutor.replace(redirectUrl, valuesMap);
        return redirectUrl;
    }

    public String callBack(String providerId, String redirectUrl, String code, HttpServletRequest httpServletRequest) {
        OAuth2ProviderConfig oAuth2ProviderConfig = getOAuthProviderConfig(providerId);
        if (StringUtils.isBlank(redirectUrl)) {
            redirectUrl = new StringBuilder().append(httpServletRequest.getScheme()).append("://").append(httpServletRequest.getServerName())
                    .append(':').append(httpServletRequest.getServerPort()).append(httpServletRequest.getContextPath())
                    .append("/services/oauth2/").append(providerId).append("/callback").toString();
        }

        String requestBody = OAuth2Helper.getAccessTokenApiRequestBody(oAuth2ProviderConfig, code, redirectUrl);

        HttpRequestDetails httpRequestDetails = HttpRequestDetailsBuilder.create(oAuth2ProviderConfig.getAccessTokenUrl())
                .setMethod("POST")
                .setContentType(ContentType.APPLICATION_FORM_URLENCODED.toString())
                .setRequestBody(requestBody).build();

        HttpResponseDetails httpResponseDetails = restConnector.invokeRestCall(httpRequestDetails);

        try {
            if (httpResponseDetails.getStatusCode() == 200) {
                String response = IOUtils.toString(httpResponseDetails.getBody());
                AccessTokenRequestContext accessTokenRequestContext = new AccessTokenRequestContext(httpResponseDetails.getHeaders().getContentType(),
                        oAuth2ProviderConfig.getAccessTokenUrl(), response);

                String accessToken = OAuth2Helper.extractAccessToken(accessTokenRequestContext);
                return OAuth2Helper.getCallbackResponse(providerId, accessToken);
            } else {
                logger.error("Failed to fetch access token, request made is {} and its response is {}", httpRequestDetails, httpResponseDetails);
                throw new WMRuntimeException("Failed to fetch access token");
            }
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to parse responseBody", e);
        } catch (JSONException e) {
            throw new WMRuntimeException("Failed to read accessToken param", e);
        }
    }

    private OAuth2ProviderConfig getOAuthProviderConfig(String providerId) {
        for (OAuth2ProviderConfig oAuth2ProviderConfig : oAuth2ProviderConfigList) {
            if (Objects.equals(oAuth2ProviderConfig.getProviderId(), providerId)) {
                return oAuth2ProviderConfig;
            }
        }
        throw new ResourceNotFoundException("No OAuth2ProviderConfig found for given providerId - " + providerId);
    }

    public void addOAuthProviderConfig(OAuth2ProviderConfig oAuth2ProviderConfig) {
        oAuth2ProviderConfigList.add(oAuth2ProviderConfig);
    }
}
