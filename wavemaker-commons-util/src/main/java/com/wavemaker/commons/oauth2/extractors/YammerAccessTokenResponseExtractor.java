package com.wavemaker.commons.oauth2.extractors;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.json.JSONUtils;

/**
 * Created by srujant on 29/8/17.
 */
public class YammerAccessTokenResponseExtractor implements AccessTokenExtractor {


    @Override
    public String getAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        String accessToken = null;
        if (StringUtils.containsIgnoreCase(accessTokenRequestContext.getAccessTokenUrl(), "yammer")) {
            try {
                HashMap<String, Object> responseMap = JSONUtils.toObject(accessTokenRequestContext.getResponseBody(), LinkedHashMap.class);
                Map<Object, Object> accessTokenObject = (Map<Object, Object>) responseMap.get("access_token");
                accessToken = (String) accessTokenObject.get("token");
            } catch (IOException e) {
                throw new WMRuntimeException(e);
            }
        }
        return accessToken;

    }

}
