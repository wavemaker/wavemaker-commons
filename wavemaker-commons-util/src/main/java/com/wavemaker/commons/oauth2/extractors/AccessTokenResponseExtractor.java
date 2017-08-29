package com.wavemaker.commons.oauth2.extractors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srujant on 24/8/17.
 */
public class AccessTokenResponseExtractor {

    private List<AccessTokenExtractor> accessTokenExtractorsList = new ArrayList<>();

    public AccessTokenResponseExtractor() {
        accessTokenExtractorsList.add(new JsonFormatAccessTokenResponseExtractor());
        accessTokenExtractorsList.add(new FormUrlEncodedFormatAccessTokenResponseExtractor());
        accessTokenExtractorsList.add(new XmlFormatAccessTokenResponseExtractor());
        accessTokenExtractorsList.add(new YammerAccessTokenResponseExtractor());
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
