package com.wavemaker.commons.auth.oauth2.extractors;

import org.springframework.http.MediaType;

/**
 * Model to store contentType and accessToken response sent by the OAuth2 authorization server.
 *
 * Created by srujant on 29/8/17.
 */
public class AccessTokenRequestContext {

    private MediaType mediaType;
    private String accessTokenUrl;
    private String responseBody;

    public AccessTokenRequestContext(MediaType mediaType, String accessTokenUrl, String responseBody) {
        this.mediaType = mediaType;
        this.accessTokenUrl = accessTokenUrl;
        this.responseBody = responseBody;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
