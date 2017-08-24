package com.wavemaker.commons.oauth2.extractors;

import org.springframework.http.MediaType;

/**
 * Created by srujant on 24/8/17.
 */
public interface AccessTokenExtractor {
    boolean canRead(MediaType mediaType);

    String getAccessToken(String accessTokenResponse);
}
