/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
