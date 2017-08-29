package com.wavemaker.commons.oauth2.extractors;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.MediaType;

import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.oauth2.OAuth2Constants;

/**
 * Created by srujant on 24/8/17.
 */
public class JsonFormatAccessTokenResponseExtractor extends MediaTypeBasedAccessTokenExtractor {


    @Override
    public boolean canRead(MediaType mediaType) {
        return MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_JSON_UTF8.equals(mediaType);
    }

    @Override
    protected String obtainAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        JSONTokener jsonTokener = new JSONTokener(accessTokenRequestContext.getResponseBody());
        JSONObject accessTokenObject = null;
        String accessToken;
        try {
            accessTokenObject = new JSONObject(jsonTokener);
            accessToken = (String) accessTokenObject.get(OAuth2Constants.ACCESS_TOKEN);
        } catch (JSONException e) {
            throw new WMRuntimeException(e);
        }
        return accessToken;
    }

}
