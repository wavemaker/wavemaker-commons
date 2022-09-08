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
package com.wavemaker.commons.core.web.rest;

import java.io.IOException;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.json.JSONUtils;

public class ErrorResponsesUtil {

    private static Logger logger = LoggerFactory.getLogger(ErrorResponsesUtil.class);

    public static String getErrorMessage(String responseBodyString) {
        String errorMessage = responseBodyString;
        try {
            ErrorResponsesWrapper errorResponsesWrapper = JSONUtils.toObject(responseBodyString, ErrorResponsesWrapper.class);
            //Always Reading only the first error
            ErrorResponse errorResponse = errorResponsesWrapper.getErrors().getError().get(0);
            errorMessage = MessageFormat.format(errorResponse.getMessage(), errorResponse.getParameters().toArray());
        } catch (IOException e) {
            logger.debug("failed to parse the error response string {}", responseBodyString);
        }
        return errorMessage;
    }
}
