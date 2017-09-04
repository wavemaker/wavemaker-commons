package com.wavemaker.commons.auth.oauth2.extractors;

import org.springframework.http.MediaType;

/**
 * Created by srujant on 29/8/17.
 */
public abstract class MediaTypeBasedAccessTokenExtractor implements AccessTokenExtractor {


    protected abstract boolean canRead(MediaType mediaType);

    protected abstract String obtainAccessToken(AccessTokenRequestContext accessTokenRequestContext);

    @Override
    public String getAccessToken(AccessTokenRequestContext accessTokenRequestContext) {

        if (canRead(accessTokenRequestContext.getMediaType())) {
            return obtainAccessToken(accessTokenRequestContext);
        }
        return null;
    }
}
