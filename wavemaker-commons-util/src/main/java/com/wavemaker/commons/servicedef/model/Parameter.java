/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.servicedef.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 27/11/15
 */
public class Parameter {

    private String name;
    private String parameterType;
    private boolean required;
    private String type;
    private boolean readOnly;
    private String contentType;

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

    public Parameter addReadOnly(final Boolean readOnly) {
        if (readOnly != null) {
            setReadOnly(readOnly);
        }
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(final boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Parameter addContentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }


    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", type='" + type + '\'' +
                ", required=" + required +
                ", readOnly=" + readOnly +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parameter)) {
            return false;
        }

        final Parameter parameter = (Parameter) o;

        if (name != null ? !name.equals(parameter.name) : parameter.name != null) {
            return false;
        }
        return type != null ? type.equals(parameter.type) : parameter.type == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
