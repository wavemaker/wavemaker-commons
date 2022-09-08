/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.auth.oauth2;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
    private OAuth2Pkce oAuth2Pkce;
    private List<Scope> scopes;
    private OAuth2Flow oauth2Flow;

    private String responseType;

    public OAuth2Pkce getoAuth2Pkce() {
        return oAuth2Pkce;
    }

    public void setoAuth2Pkce(OAuth2Pkce oAuth2Pkce) {
        this.oAuth2Pkce = oAuth2Pkce;
    }

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
        if (StringUtils.isNotBlank(authorizationUrl)) {
            return authorizationUrl;
        }
        return "";
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

    public OAuth2Flow getOauth2Flow() {
        return oauth2Flow;
    }

    public void setOauth2Flow(OAuth2Flow oauth2Flow) {
        this.oauth2Flow = oauth2Flow;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
