package com.wavemaker.commons.auth.oauth2;

import java.util.List;

/**
 * da
 * Created by srujant on 18/7/17.
 */
public class OAuth2ProviderConfig {

    private String providerId;
    private String authorizationUrl;
    private String accessTokenUrl;
    private String clientId;
    private String clientSecret;
    private String sendAccessTokenAs;
    private String accessTokenParamName;
    private List<Scope> scopes;


    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(List<Scope> scopes) {
        this.scopes = scopes;
    }

    public String getSendAccessTokenAs() {
        return sendAccessTokenAs;
    }

    public void setSendAccessTokenAs(String sendAccessTokenAs) {
        this.sendAccessTokenAs = sendAccessTokenAs;
    }

    public String getAccessTokenParamName() {
        return accessTokenParamName;
    }

    public void setAccessTokenParamName(String accessTokenParamName) {
        this.accessTokenParamName = accessTokenParamName;
    }
}
