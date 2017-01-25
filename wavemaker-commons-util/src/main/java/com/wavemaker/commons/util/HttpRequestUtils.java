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
        Map<String, Object> errorMap = new HashMap<String, Object>(1);
        ErrorResponse errorResponse = getErrorResponse(messageResource);
        writeErrorResponse(responseCode, response, errorResponse);
    }

    public static void writeJsonErrorResponse(String message, int responseCode, HttpServletResponse response) throws IOException{
        ErrorResponse errorResponse = getErrorResponse(message);
        writeErrorResponse(responseCode, response, errorResponse);
    }

    private static void writeErrorResponse(final int responseCode, final HttpServletResponse response, final ErrorResponse errorResponse) throws IOException {
        Map<String, Object> errorMap = new HashMap<String, Object>(1);
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
        errorResponse.setParameters(new ArrayList<String>(0));
        return errorResponse;
    }


}
