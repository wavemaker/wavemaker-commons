package com.wavemaker.commons.oauth2.extractors;

import org.springframework.http.MediaType;

import com.wavemaker.commons.oauth2.OAuth2Constants;

/**
 * Created by srujant on 24/8/17.
 */
public class FormUrlEncodedFormatAccessTokenResponseExtractor extends MediaTypeBasedAccessTokenExtractor {

    @Override
    public boolean canRead(MediaType mediaType) {
        return MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(mediaType.getType() + "/" + mediaType.getSubtype());
    }

    @Override
    protected String obtainAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        String accessToken = null;
        String[] response = accessTokenRequestContext.getResponseBody().split("&");
        for (String string : response) {
            if (string.contains(OAuth2Constants.ACCESS_TOKEN)) {
                accessToken = string.substring(string.indexOf("=") + 1);
                break;
            }
        }
        return accessToken;
    }
}
