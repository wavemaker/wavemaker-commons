package com.wavemaker.studio.common.swaggerdoc.handler;

import java.util.ArrayList;
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
        Response successResponse = response.get(SUCCESS_RESPONSE_CODE);
        Property property = successResponse.getSchema();
        if (property instanceof RefProperty) {
            RefProperty refProperty = (RefProperty) property;
            Model model = models.get(refProperty.getSimpleRef());
            return ((AbstractModel) model).getFullyQualifiedName();
        } else {
            return property.getType();
        }
    }

    public List<String> getFullyQualifiedArgumentReturnType() {
        Map<String, Response> response = operation.getResponses();
        Response successResponse = response.get(SUCCESS_RESPONSE_CODE);
        Property property = successResponse.getSchema();
        if (property instanceof RefProperty) {
            RefProperty refProperty = (RefProperty) property;
            List<Property> typeArgumentsProperties = refProperty.getTypeArguments();
            if (typeArgumentsProperties.size() == 0) {
                return null;
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
            return null;
        }
    }

}
