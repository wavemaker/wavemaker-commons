/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
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
package com.wavemaker.commons.swaggerdoc.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.wavemaker.commons.SwaggerException;
import com.wavemaker.tools.apidocs.tools.core.model.ComposedModel;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.Path;
import com.wavemaker.tools.apidocs.tools.core.model.RefModel;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.AbstractParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.properties.DateProperty;
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
    public static final String STRING = "string";
    private static final String INT64 = "int64";
    private static final String INT32 = "int32";
    private static final String INTEGER = "integer";
    private static final String FLOAT = "float";
    private static final String OPERATION_DOES_NOT_EXIST_WITH_ID = "Operation does not exist with id ";
    private static final String DATE_TIME_TYPE = "date-time";
    private static final String DOUBLE_TYPE = "double";
    private static final String NUMBER_TYPE = "number";

    enum OperationType {
        GET, POST, DELETE, PUT, PATCH, OPTIONS
    }

    /**
     * Return Path from the Swagger object based on endPoint
     *
     * @param swagger : Searches in Swagger Object
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
     * @param swagger : searches in Swagger Object
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
        throw new SwaggerException(OPERATION_DOES_NOT_EXIST_WITH_ID + operationUid);
    }

    /**
     * Return Operation from the Swagger object based on first occurrence in paths
     *
     * @param swagger : searches in Swagger Object
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
        throw new SwaggerException(OPERATION_DOES_NOT_EXIST_WITH_ID + operationUid);
    }

    /**
     * Return Operation from the Swagger object based on operationUid in provided endPoint.
     *
     * @param swagger : Searches in Swagger Object
     * @param endPoint :   search operation in this endPoint
     * @param operationUid : search based on operationUid
     * @return Operation
     */
    public static Operation getOperation(Swagger swagger, String endPoint, String operationUid) {
        Path path = Objects
                .requireNonNull(swagger.getPaths().get(endPoint), "Endpoint does not exist with id " + endPoint);
        for (Operation operation : path.getOperations()) {
            if (operationUid.equals(operation.getOperationId())) {
                return operation;
            }
        }
        throw new SwaggerException(OPERATION_DOES_NOT_EXIST_WITH_ID + operationUid);
    }

    /**
     * Return method type of the operation in a path
     *
     * @param path : searches in path object
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

        OperationType operationType = null;
        if (operationGet != null && operationGet.getOperationId().equals(operationUid)) {
            operationType = OperationType.GET;
        }
        if (operationPost != null && operationPost.getOperationId().equals(operationUid)) {
            operationType = OperationType.POST;
        }
        if (operationDelete != null && operationDelete.getOperationId().equals(operationUid)) {
            operationType = OperationType.DELETE;
        }
        if (operationPatch != null && operationPatch.getOperationId().equals(operationUid)) {
            operationType = OperationType.PATCH;
        }
        if (operationPut != null && operationPut.getOperationId().equals(operationUid)) {
            operationType = OperationType.PUT;
        }
        if (operationOptions != null && operationOptions.getOperationId().equals(operationUid)) {
            operationType = OperationType.OPTIONS;
        }

        if (operationType != null) {
            return operationType.name().toLowerCase();
        } else {
            throw new SwaggerException(OPERATION_DOES_NOT_EXIST_WITH_ID + operationUid);
        }
    }

    /**
     * Builds property based on given type and format.
     *
     * @param type : type of the property
     * @param format : format of the property
     * @return Property, may returns null if type and format is not based on swagger property.
     */
    public static Property buildProperty(String type, String format) {
        if ("boolean".equals(type)) {
            return PropertyBuilder.build(type, null, null);
        }
        if ("date".equals(type)) {
            return PropertyBuilder.build(STRING, "date", null);
        }
        if (DATE_TIME_TYPE.equals(type)) {
            return PropertyBuilder.build(STRING, DATE_TIME_TYPE, null);
        }
        if (DOUBLE_TYPE.equals(type)) {
            return PropertyBuilder.build(NUMBER_TYPE, DOUBLE_TYPE, null);
        }
        if (FLOAT.equals(type)) {
            return PropertyBuilder.build(NUMBER_TYPE, FLOAT, null);
        }
        if ("file".equals(type)) {
            return PropertyBuilder.build("file", null, null);
        }
        if (NUMBER_TYPE.equals(type)) {
            return PropertyBuilder.build(NUMBER_TYPE, null, null);
        }
        if (INTEGER.equals(type) && INT32.equals(format)) {
            return PropertyBuilder.build(INTEGER, format, null);
        }
        if (INTEGER.equals(type) && INT64.equals(format)) {
            return PropertyBuilder.build(INTEGER, format, null);
        }
        if (INTEGER.equals(type)) {
            return PropertyBuilder.build(INTEGER, INT32, null);
        }
        if ("long".equals(type)) {
            return PropertyBuilder.build(INTEGER, INT64, null);
        }
        if ("$ref".equals(type)) {
            return PropertyBuilder.build("$ref", format, null);
        }
        if (STRING.equals(type) && "uuid".equals(format)) {
            return PropertyBuilder.build(STRING, "uuid", null);
        }
        if (STRING.equals(type)) {
            return PropertyBuilder.build(STRING, null, null);
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

        if (property instanceof DateProperty) {
            DateProperty dateProperty = (DateProperty) property;
            return findDateType(dateProperty).getName();
        }
        if ("boolean".equals(type)) {
            return Boolean.class.getName();
        }
        if (NUMBER_TYPE.equals(type) && DOUBLE_TYPE.equals(format)) {
            return Double.class.getName();
        }
        if (NUMBER_TYPE.equals(type) && FLOAT.equals(format)) {
            return Float.class.getName();
        }
        if (INTEGER.equals(type) && INT32.equals(format)) {
            return Integer.class.getName();
        }
        if (INTEGER.equals(type) && INT64.equals(format)) {
            return Long.class.getName();
        }
        if (STRING.equals(type) && "uuid".equals(format)) {
            return String.class.getName();
        }
        if (STRING.equals(type)) {
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
            } else if (STRING.equals(formParameter.getType()) && ((AbstractParameter) parameter)
                    .getFullyQualifiedType() != null) {
                fullyQualifiedType = ((AbstractParameter) parameter).getFullyQualifiedType();
            }
        } else {
            fullyQualifiedType = ((AbstractParameter) parameter).getFullyQualifiedType();
        }
        return (fullyQualifiedType == null) ? String.class.getSimpleName() : fullyQualifiedType;
    }

    public static Class<?> findDateType(final DateProperty property) {
        Class<?> javaType = java.sql.Date.class;
        if (DATE_TIME_TYPE.equals(property.getSubFormat())) {
            javaType = LocalDateTime.class;
        } else if ("time".equals(property.getSubFormat())) {
            javaType = Time.class;
        } else if ("timestamp".equals(property.getSubFormat())) {
            javaType = Timestamp.class;
        }
        return javaType;
    }

    public static Map<String, Property> getProperties(Swagger swagger, Model model) {
        Map<String, Property> propertyMap = new LinkedHashMap<>();

        if (model instanceof ComposedModel) {
            final List<Model> parentModels = ((ComposedModel) model).getAllOf();

            for (final Model parent : parentModels) {
                propertyMap.putAll(getProperties(swagger, parent));
            }
        } else if (model instanceof RefModel) {
            final Model refModel = swagger.getDefinitions().get(((RefModel) model).getSimpleRef());
            if (refModel != null) {
                propertyMap.putAll(getProperties(swagger, refModel));
            }
        }

        if (model.getProperties() != null) {
            propertyMap.putAll(model.getProperties());
        }

        return propertyMap;
    }


}
