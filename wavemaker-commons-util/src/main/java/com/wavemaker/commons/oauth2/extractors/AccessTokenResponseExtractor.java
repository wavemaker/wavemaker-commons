package com.wavemaker.commons.oauth2.extractors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;

import com.wavemaker.commons.WMRuntimeException;

/**
 * Created by srujant on 24/8/17.
 */
public class AccessTokenResponseExtractor {

    private List<AccessTokenExtractor> accessTokenExtractorsList = new ArrayList<>();

    public AccessTokenResponseExtractor() {
        accessTokenExtractorsList.add(new JsonFormatAccessTokenResponseExtractor());
        accessTokenExtractorsList.add(new FormUrlEncodedFormatAccessTokenResponseExtractor());
        accessTokenExtractorsList.add(new XmlFormatAccessTokenResponseExtractor());
    }

    public String getAccessToken(String accessTokenResponse, MediaType mediaType) {
        String accessToken = null;
        for (AccessTokenExtractor accessTokenExtractor : accessTokenExtractorsList) {
            if (accessTokenExtractor.canRead(mediaType)) {
                accessToken = accessTokenExtractor.getAccessToken(accessTokenResponse);
                break;
            }
        }
        if (accessToken == null) {
            throw new WMRuntimeException("No AccessTokenResponseExtractor found for MediaType " + mediaType);
        }
        return accessToken;
    }
}
