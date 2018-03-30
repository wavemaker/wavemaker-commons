package com.wavemaker.commons.auth.oauth2.extractors;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.wavemaker.commons.auth.oauth2.OAuth2Constants;
import com.wavemaker.commons.json.JSONUtils;

/**
 * Created by srujant on 24/8/17.
 */
public class JsonFormatAccessTokenExtractor extends MediaTypeBasedAccessTokenExtractor {


    @Override
    public boolean canRead(MediaType mediaType) {
        return MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_JSON_UTF8.equals(mediaType);
    }

    @Override
    protected String obtainAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        JsonNode jsonNode = JSONUtils.readTree(accessTokenRequestContext.getResponseBody());
        if (jsonNode.has(OAuth2Constants.ACCESS_TOKEN)) {
            JsonNode textNode = jsonNode.get(OAuth2Constants.ACCESS_TOKEN);
            return textNode.asText();
        }
        return null;
    }
}
