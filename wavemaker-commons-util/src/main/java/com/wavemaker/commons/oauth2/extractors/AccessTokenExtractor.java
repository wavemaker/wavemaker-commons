package com.wavemaker.commons.oauth2.extractors;

/**
 * Created by srujant on 24/8/17.
 */
public interface AccessTokenExtractor {
    String getAccessToken(AccessTokenRequestContext accessTokenRequestContext);
}