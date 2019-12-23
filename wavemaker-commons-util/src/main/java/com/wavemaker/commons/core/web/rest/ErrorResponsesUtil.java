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
