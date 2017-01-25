package com.wavemaker.commons.servicedef.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 27/11/15
 */
public class Parameter {

    private String name;
    private String parameterType;
    private String type;
    private boolean required;

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

    public Parameter addRequired(final boolean required) {
        this.required = required;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", type='" + type + '\'' +
                ", required=" + required +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter)) return false;

        final Parameter parameter = (Parameter) o;

        if (name != null ? !name.equals(parameter.name) : parameter.name != null) return false;
        if (parameterType != null ? !parameterType.equals(parameter.parameterType) : parameter.parameterType != null)
            return false;
        return type != null ? type.equals(parameter.type) : parameter.type == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parameterType != null ? parameterType.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
