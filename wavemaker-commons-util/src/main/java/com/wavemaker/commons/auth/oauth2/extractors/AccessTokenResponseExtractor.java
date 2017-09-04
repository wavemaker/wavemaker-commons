package com.wavemaker.commons.auth.oauth2.extractors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srujant on 24/8/17.
 */
public class AccessTokenResponseExtractor {

    private List<AccessTokenExtractor> accessTokenExtractorsList = new ArrayList<>();

    public AccessTokenResponseExtractor() {
        accessTokenExtractorsList.add(new JsonFormatAccessTokenExtractor());
        accessTokenExtractorsList.add(new FormUrlEncodedFormatAccessTokenExtractor());
        accessTokenExtractorsList.add(new XmlFormatAccessTokenExtractor());
        accessTokenExtractorsList.add(new YammerAccessTokenExtractor());
    }

    public String getAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        String accessToken = null;
        for (AccessTokenExtractor accessTokenExtractor : accessTokenExtractorsList) {
            accessToken = accessTokenExtractor.getAccessToken(accessTokenRequestContext);
            if (accessToken != null) {
                return accessToken;
            }
        }
        return accessToken;
    }
}
