package com.wavemaker.commons.swaggerdoc.handler;

import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.tools.apidocs.tools.core.model.Path;

/**
 * Created by sunilp on 4/6/15.
 */
public class PathHandler {

    private final String pathName;
    private final Path path;

    public PathHandler(String pathName, Path path) {
        this.pathName = pathName;
        this.path = path;
    }

    public String getOperationType(String operationId) {
        if (path.getGet() != null && path.getGet().getOperationId().equals(operationId)) {
            return "get";
        }
        if (path.getPost() != null && path.getPost().getOperationId().equals(operationId)) {
            return "post";
        }
        if (path.getDelete() != null && path.getDelete().getOperationId().equals(operationId)) {
            return "delete";
        }
        if (path.getPatch() != null && path.getPatch().getOperationId().equals(operationId)) {
            return "patch";
        }
        if (path.getPut() != null && path.getPut().getOperationId().equals(operationId)) {
            return "put";
        }
        if (path.getOptions() != null && path.getOptions().getOperationId().equals(operationId)) {
            return "options";
        }
        throw new WMRuntimeException("Operation does not exist with id " + operationId);
    }
}
