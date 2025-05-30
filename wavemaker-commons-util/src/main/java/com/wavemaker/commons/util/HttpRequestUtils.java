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
package com.wavemaker.commons.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.core.web.rest.ErrorResponse;
import com.wavemaker.commons.core.web.rest.ErrorResponses;
import com.wavemaker.commons.json.JSONUtils;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 11/3/16
 */
public class HttpRequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    private HttpRequestUtils() {
    }

    public static void writeJsonErrorResponse(MessageResource messageResource, int responseCode, HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = getErrorResponse(messageResource);
        writeErrorResponse(responseCode, response, errorResponse);
    }

    public static void writeJsonErrorResponse(String message, int responseCode, HttpServletResponse response) throws IOException {
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
        try {
            response.getWriter().write(JSONUtils.toJSON(errorMap));
        } catch (IllegalStateException e) {
            logger.warn("Error response: {}", JSONUtils.toJSON(errorMap));
        }
    }

    private static ErrorResponse getErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        return errorResponse;
    }

    private static ErrorResponse getErrorResponse(MessageResource messageResource) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessageKey(messageResource.getMessageKey());
        errorResponse.setMessage(messageResource.getMessageWithPlaceholders());
        errorResponse.setParameters(new ArrayList<>(0));
        return errorResponse;
    }

    public static String getBaseUrl(String requestUrl) {
        if (Objects.nonNull(requestUrl)) {
            try {
                URI uri = new URI(requestUrl);
                return getBaseUrl(uri);
            } catch (URISyntaxException e) {
                throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.invalid.request.url"), e);
            }
        }
        return null;
    }

    public static String getBaseUrl(URI uri) {
        StringBuilder stringBuilder = new StringBuilder(uri.getScheme()).append("://").append(uri.getHost());
        int serverPort = uri.getPort();
        if (serverPort != 80 && serverPort != 443 && serverPort != -1) {
            stringBuilder.append(":").append(serverPort).toString();
        }
        return stringBuilder.toString();
    }

    public static String getBaseUrl(HttpServletRequest httpServletRequest) {
        StringBuilder sb = new StringBuilder(httpServletRequest.getScheme()).append("://").append(httpServletRequest.getServerName());
        int serverPort = httpServletRequest.getServerPort();
        if (serverPort != 80 && serverPort != 443) {
            sb.append(":").append(serverPort).toString();
        }
        return sb.toString();
    }

    public static String getApplicationBaseUrl(HttpServletRequest httpServletRequest) {
        return new StringBuilder(getBaseUrl(httpServletRequest)).append(httpServletRequest.getContextPath()).toString();
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
