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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.wavemaker.tools.apidocs.tools.core.model.AbstractModel;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.Response;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.model.properties.RefProperty;

/**
 * Created by sunilp on 29/5/15.
 */
public class OperationHandler {

    private static final String SUCCESS_RESPONSE_CODE = "200";

    private final Operation operation;
    private final Map<String, Model> models;

    public OperationHandler(Operation operation, Map<String, Model> models) {
        this.operation = operation;
        this.models = models;
    }

    public String getFullyQualifiedReturnType() {
        Map<String, Response> response = operation.getResponses();
        if(response!=null) {
            Response successResponse = response.get(SUCCESS_RESPONSE_CODE);
            if (successResponse != null) {
                Property property = successResponse.getSchema();
                if (property instanceof RefProperty) {
                    RefProperty refProperty = (RefProperty) property;
                    Model model = models.get(refProperty.getSimpleRef());
                    return ((AbstractModel) model).getFullyQualifiedName();
                } else {
                    return property.getType();
                }
            }
        }
        return null;
    }

    public List<String> getFullyQualifiedArgumentReturnType() {
        Map<String, Response> response = operation.getResponses();
        Response successResponse = response.get(SUCCESS_RESPONSE_CODE);
        Property property = successResponse.getSchema();
        if (property instanceof RefProperty) {
            RefProperty refProperty = (RefProperty) property;
            List<Property> typeArgumentsProperties = refProperty.getTypeArguments();
            if (typeArgumentsProperties.isEmpty()) {
                return Collections.emptyList();
            }
            List<String> argumentTypeList = new ArrayList<>();
            for(Property argProperty : typeArgumentsProperties)
            {
                if (property instanceof RefProperty) {
                    RefProperty refArgProperty = (RefProperty) property;
                    Model argModel = models.get(refArgProperty.getSimpleRef());
                    argumentTypeList.add(((AbstractModel) argModel).getFullyQualifiedName());
                } else {
                    argumentTypeList.add(argProperty.getType());
                }
            }
            return argumentTypeList;
        } else {
            return Collections.emptyList();
        }
    }

}
