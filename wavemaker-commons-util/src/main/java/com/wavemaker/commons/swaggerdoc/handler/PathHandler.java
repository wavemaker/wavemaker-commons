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