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
package com.wavemaker.commons.servicedef.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 13/2/16
 */
public class ServiceDefinition {

    private String id;

    private String service;

    private String controller;

    private String type;

    private CrudOperation crudOperation;

    private WMServiceOperationInfo wmServiceOperationInfo;

    @JsonIgnore
    public static ServiceDefinition getNewInstance() {
        return new ServiceDefinition();
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public ServiceDefinition addId(final String id) {
        this.id = id;
        return this;
    }

    public CrudOperation getCrudOperation() {
        return crudOperation;
    }

    public void setCrudOperation(CrudOperation crudOperation) {
        this.crudOperation = crudOperation;
    }

    public ServiceDefinition addCrudOperation(final CrudOperation crudOperation) {
        this.crudOperation = crudOperation;
        return this;
    }

    public WMServiceOperationInfo getWmServiceOperationInfo() {
        return wmServiceOperationInfo;
    }

    public void setWmServiceOperationInfo(final WMServiceOperationInfo wmServiceOperationInfo) {
        this.wmServiceOperationInfo = wmServiceOperationInfo;
    }

    public ServiceDefinition addWmServiceOperationInfo(final WMServiceOperationInfo wmServiceOperationInfo) {
        this.wmServiceOperationInfo = wmServiceOperationInfo;
        return this;
    }

    public String getService() {
        return service;
    }

    public void setService(final String service) {
        this.service = service;
    }

    public ServiceDefinition addService(final String service) {
        this.service = service;
        return this;
    }

    public String getController() {
        return controller;
    }

    public void setController(final String controller) {
        this.controller = controller;
    }

    public ServiceDefinition addController(final String controller) {
        this.controller = controller;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public ServiceDefinition addType(final String type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceDefinition)) {
            return false;
        }

        final ServiceDefinition that = (ServiceDefinition) o;

        if(id == null || that.getId() == null) {
            return false;
        }

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
