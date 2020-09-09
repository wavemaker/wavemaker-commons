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
package com.wavemaker.commons.rest.error;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.wavemaker.commons.util.WMIOUtils;


/**
 * @author Uday Shankar
 */
public class WMDefaultResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(WMDefaultResponseErrorHandler.class);

    @Override
    protected byte[] getResponseBody(ClientHttpResponse response) {
        InputStream inputStream = null;
        try {
            inputStream = response.getBody();
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            logger.error("Failed to read error response", e);
        } finally {
            WMIOUtils.closeSilently(inputStream);
        }
        return new byte[0];
    }
}
