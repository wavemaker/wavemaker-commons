package com.wavemaker.commons.auth.oauth2.extractors;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by srujant on 24/8/17.
 */
public class AccessTokenResponseExtractor {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenResponseExtractor.class);
    private List<AccessTokenExtractor> accessTokenExtractorsList = new ArrayList<>();

    public AccessTokenResponseExtractor() {
        accessTokenExtractorsList.add(new JsonFormatAccessTokenExtractor());
        accessTokenExtractorsList.add(new FormUrlEncodedFormatAccessTokenExtractor());
        accessTokenExtractorsList.add(new XmlFormatAccessTokenExtractor());
        accessTokenExtractorsList.add(new AccessTokenInObjectExtractor());
    }

    /**
    * Extracts access_token from {@link com.wavemaker.commons.auth.oauth2.extractors.AccessTokenRequestContext #getResonseBody()} using <code>
    * {@link AccessTokenExtractor}.
    *
    * */
    public String getAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        String accessToken = null;
        for (AccessTokenExtractor accessTokenExtractor : accessTokenExtractorsList) {
            logger.debug("Using {} to extract accessToken", accessTokenExtractor.getClass());
            accessToken = accessTokenExtractor.getAccessToken(accessTokenRequestContext);
            if (accessToken != null) {
                logger.debug("Found accessToken using {}", accessTokenExtractor.getClass());
                return accessToken;
            }
        }
        return accessToken;
    }
}
