package com.wavemaker.studio.common.servicedef.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 27/11/15
 */
public class Parameter {

    private String name;
    private String parameterType;
    private String type;

    @JsonIgnore
    public static synchronized Parameter getNewInstance() {
        return new Parameter();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parameter addName(final String name) {
        this.name = name;
        return this;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public Parameter addParameterType(final String parameterType) {
        this.parameterType = parameterType;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Parameter addType(final String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
