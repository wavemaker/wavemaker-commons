/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.core.web.rest.ErrorResponse;
import com.wavemaker.commons.core.web.rest.ErrorResponses;
import com.wavemaker.commons.json.JSONUtils;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 11/3/16
 */
public class HttpRequestUtils {

    public static void writeJsonErrorResponse(MessageResource messageResource, int responseCode, HttpServletResponse response) throws IOException {
        Map<String, Object> errorMap = new HashMap<>(1);
        ErrorResponse errorResponse = getErrorResponse(messageResource);
        writeErrorResponse(responseCode, response, errorResponse);
    }

    public static void writeJsonErrorResponse(String message, int responseCode, HttpServletResponse response) throws IOException{
        ErrorResponse errorResponse = getErrorResponse(message);
        writeErrorResponse(responseCode, response, errorResponse);
    }

    private static void writeErrorResponse(final int responseCode, final HttpServletResponse response, final ErrorResponse errorResponse) throws IOException {
        Map<String, Object> errorMap = new HashMap<>(1);
        List<ErrorResponse> errorResponseList = new ArrayList<>(1);
        errorResponseList.add(errorResponse);
        errorMap.put("errors", new ErrorResponses(errorResponseList));

        response.setStatus(responseCode);
        response.setContentType("application/json");
        response.getWriter().write(JSONUtils.toJSON(errorMap));
    }

    private static ErrorResponse getErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        return errorResponse;
    }

    private static ErrorResponse getErrorResponse(MessageResource messageResource) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessageKey(messageResource.getMessageKey());
        errorResponse.setParameters(new ArrayList<>(0));
        return errorResponse;
    }


}
