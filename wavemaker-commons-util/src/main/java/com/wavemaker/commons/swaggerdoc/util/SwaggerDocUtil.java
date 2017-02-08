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
package com.wavemaker.commons.swaggerdoc.util;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import com.wavemaker.commons.SwaggerException;
import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.Path;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.AbstractParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.model.properties.PropertyBuilder;

/**
 * SwaggerDocUtils is used to provided required component/s from the swagger document.
 * <p/>
 * Created by sunilp on 26/5/15.
 */
public class SwaggerDocUtil {

    public static final String FILE = "file";
    public static final String ARRAY = "array";

    enum OperationType {
        get, post, delete, put, patch, options
    }

    /**
     * Return Path from the Swagger object based on endPoint
     *
     * @param swagger  : Searches in Swagger Object
     * @param endPoint : search based on endPoint
     * @return Path
     */
    public static Path getPathByEndPoint(Swagger swagger, String endPoint) {
        for (Map.Entry<String, Path> pathEntry : swagger.getPaths().entrySet()) {
            if (pathEntry.getKey().equals(endPoint)) {
                return pathEntry.getValue();
            }
        }
        throw new SwaggerException("Path does not exist with end point " + endPoint);
    }

    /**
     * Return Operation from the Path object based on first occurrence in paths
     *
     * @param swagger      : searches in Swagger Object
     * @param operationUid : search based on operationUid
     * @return path
     */
    public static Path getPathByOperationUid(Swagger swagger, String operationUid) {
        for (Map.Entry<String, Path> pathEntry : swagger.getPaths().entrySet()) {
            for (Operation operation : pathEntry.getValue().getOperations()) {
                if (operationUid.equals(operation.getOperationId())) {
                    return pathEntry.getValue();
                }
            }
        }
        throw new SwaggerException("Operation does not exist with id " + operationUid);
    }

    /**
     * Return Operation from the Swagger object based on first occurrence in paths
     *
     * @param swagger      : searches in Swagger Object
     * @param operationUid : search based on operationUid
     * @return Operation
     */
    public static Operation getOperationByUid(Swagger swagger, String operationUid) {
        for (Map.Entry<String, Path> pathEntry : swagger.getPaths().entrySet()) {
            for (Operation operation : pathEntry.getValue().getOperations()) {
                if (operationUid.equals(operation.getOperationId())) {
                    return operation;
                }
            }
        }
        throw new SwaggerException("Operation does not exist with id " + operationUid);
    }

    /**
     * Return Operation from the Swagger object based on operationUid in provided endPoint.
     *
     * @param swagger      : Searches in Swagger Object
     * @param endPoint     :   search operation in this endPoint
     * @param operationUid : search based on operationUid
     * @return Operation
     */
    public static Operation getOperation(Swagger swagger, String endPoint, String operationUid) {
        Path path = Objects.requireNonNull(swagger.getPaths().get(endPoint), "Endpoint does not exist with id " + endPoint);
        for (Operation operation : path.getOperations()) {
            if (operationUid.equals(operation.getOperationId())) {
                return operation;
            }
        }
        throw new SwaggerException("Operation does not exist with id " + operationUid);
    }

    /**
     * Return method type of the operation in a path
     *
     * @param path         : searches in path object
     * @param operationUid : search operation based on this id.
     * @return method type
     */
    public static String getOperationType(Path path, String operationUid) {
        Operation operationGet = path.getGet();
        Operation operationPost = path.getPost();
        Operation operationDelete = path.getDelete();
        Operation operationPatch = path.getPatch();
        Operation operationPut = path.getPut();
        Operation operationOptions = path.getOptions();

        if (operationGet != null && operationGet.getOperationId().equals(operationUid)) {
            return OperationType.get.name();
        }
        if (operationPost != null && operationPost.getOperationId().equals(operationUid)) {
            return OperationType.post.name();
        }
        if (operationDelete != null && operationDelete.getOperationId().equals(operationUid)) {
            return OperationType.delete.name();
        }
        if (operationPatch != null && operationPatch.getOperationId().equals(operationUid)) {
            return OperationType.patch.name();
        }
        if (operationPut != null && operationPut.getOperationId().equals(operationUid)) {
            return OperationType.put.name();
        }
        if (operationOptions != null && operationOptions.getOperationId().equals(operationUid)) {
            return OperationType.options.name();
        }
        throw new SwaggerException("Operation does not exist with id " + operationUid);
    }

    /**
     * Builds property based on given type and format.
     *
     * @param type   : type of the property
     * @param format : format of the property
     * @return Property, may returns null if type and format is not based on swagger property.
     */
    public static Property buildProperty(String type, String format) {
        if ("boolean".equals(type)) {
            return PropertyBuilder.build(type, null, null);
        }
        if ("date".equals(type)) {
            return PropertyBuilder.build("string", "date", null);
        }
        if ("date-time".equals(type)) {
            return PropertyBuilder.build("string", "date-time", null);
        }
        if ("double".equals(type)) {
            return PropertyBuilder.build("number", "double", null);
        }
        if ("float".equals(type)) {
            return PropertyBuilder.build("number", "float", null);
        }
        if ("file".equals(type)) {
            return PropertyBuilder.build("file", null, null);
        }
        if ("number".equals(type)) {
            return PropertyBuilder.build("number", null, null);
        }
        if ("integer".equals(type) && "int32".equals(format)) {
            return PropertyBuilder.build("integer", format, null);
        }
        if ("integer".equals(type) && "int64".equals(format)) {
            return PropertyBuilder.build("integer", format, null);
        }
        if ("integer".equals(type)) {
            return PropertyBuilder.build("integer", "int32", null);
        }
        if ("long".equals(type)) {
            return PropertyBuilder.build("integer", "int64", null);
        }
        if ("$ref".equals(type)) {
            return PropertyBuilder.build("$ref", format, null);
        }
        if ("string".equals(type)) {
            return PropertyBuilder.build("string", null, null);
        }
        if ("string".equals(type) && "uuid".equals(format)) {
            return PropertyBuilder.build("string", "uuid", null);
        }
        return null;

    }

    /**
     * Constructs fully qualified name for the give property based on property type and format.
     *
     * @param property
     * @return fully qualified name for given property,Returns null if type and format is not based on swagger property.
     */
    public static String getWrapperPropertyFQType(Property property) {
        String type = property.getType();
        String format = property.getFormat();

        if ("boolean".equals(type)) {
            return Boolean.class.getName();
        }
        if ("string".equals(type) && "date".equals(format)) {
            return Date.class.getName();
        }
        if ("string".equals(type) && "date-time".equals(format)) {
            return String.class.getName();
        }
        if ("number".equals(type) && "double".equals(format)) {
            return Double.class.getName();
        }
        if ("number".equals(type) && "float".equals(format)) {
            return Float.class.getName();
        }
        if ("integer".equals(type) && "int32".equals(format)) {
            return Integer.class.getName();
        }
        if ("integer".equals(type) && "int64".equals(format)) {
            return Long.class.getName();
        }
        if ("string".equals(type) && "uuid".equals(format)) {
            return String.class.getName();
        }
        if ("string".equals(type)) {
            return String.class.getName();
        }
        return null;
    }

    public static String getParameterType(final Parameter parameter) {
        String fullyQualifiedType = null;
        //if parameter is Integer[] array or any array
        if (parameter instanceof FormParameter) {
            FormParameter formParameter = (FormParameter) parameter;
            if (ARRAY.equals(formParameter.getType())) {
                fullyQualifiedType = formParameter.getItems().getType();
            } else if (FILE.equals(formParameter.getType())) {
                fullyQualifiedType = formParameter.getType();
            }
        } else {
            fullyQualifiedType = ((AbstractParameter) parameter).getFullyQualifiedType();
        }
        return (fullyQualifiedType == null) ? String.class.getSimpleName() : fullyQualifiedType;
    }

}