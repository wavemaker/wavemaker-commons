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

/**
 * Created by srujant on 29/8/17.
 */
public abstract class MediaTypeBasedAccessTokenExtractor implements AccessTokenExtractor {


    protected abstract boolean canRead(MediaType mediaType);

    protected abstract String obtainAccessToken(AccessTokenRequestContext accessTokenRequestContext);

    @Override
    public String getAccessToken(AccessTokenRequestContext accessTokenRequestContext) {

        if (canRead(accessTokenRequestContext.getMediaType())) {
            return obtainAccessToken(accessTokenRequestContext);
        }
        return null;
    }
}
