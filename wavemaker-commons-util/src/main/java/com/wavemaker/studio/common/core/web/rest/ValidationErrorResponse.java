package com.wavemaker.studio.common.core.web.rest;

import java.util.Map;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 26/4/16
 */
public class ValidationErrorResponse extends ErrorResponse {

    private Map<String, String> paths;

    public ValidationErrorResponse(final String id, final String message, final Map<String, String> paths) {
        super(id, message);
        this.paths = paths;
    }

    public Map<String, String> getPaths() {
        return paths;
    }

    public void setPaths(final Map<String, String> paths) {
        this.paths = paths;
    }
}
