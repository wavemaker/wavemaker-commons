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

import org.springframework.http.MediaType;

import com.wavemaker.commons.auth.oauth2.OAuth2Constants;

/**
 * Created by srujant on 24/8/17.
 */
public class FormUrlEncodedFormatAccessTokenExtractor extends MediaTypeBasedAccessTokenExtractor {

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
                accessToken = string.substring(string.indexOf('=') + 1);
                break;
            }
        }
        return accessToken;
    }
}
