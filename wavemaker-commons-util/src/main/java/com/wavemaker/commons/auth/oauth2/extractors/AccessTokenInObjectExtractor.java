package com.wavemaker.commons.auth.oauth2.extractors;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.wavemaker.commons.json.JSONUtils;

/**
 * Extract's access token from access_token.token path.
 * 
 * Used in extracting access token for yammer
 * Created by srujant on 29/8/17.
 */
public class AccessTokenInObjectExtractor implements AccessTokenExtractor {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenInObjectExtractor.class);
    
    @Override
    public String getAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        String accessToken = null;
        try {
            HashMap<String, Object> responseMap = JSONUtils.toObject(accessTokenRequestContext.getResponseBody(), LinkedHashMap.class);
            Object accessTokenObject = responseMap.get("access_token");
            if (accessTokenObject instanceof Map) {
                Map<Object, Object> accessTokenMap = (Map<Object, Object>) accessTokenObject;
                accessToken = (String) accessTokenMap.get("token");
            }
        } catch (JsonMappingException e) {
            //do nothing
        } catch (IOException ioe) {
            logger.warn("Failed to extract access_token.token path in json string", ioe);
        }
        return accessToken;

    }

}
