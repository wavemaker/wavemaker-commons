/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
