package com.wavemaker.studio.common.servicedef.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 13/2/16
 */
public class ServiceDefinition {

    private String id;

    private String service;

    private String controller;

    private String operationType;

    private String type;

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

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(final String operationType) {
        this.operationType = operationType;
    }

    public ServiceDefinition addOperationType(final String operationType) {
        this.operationType = operationType;
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
        if (this == o) return true;
        if (!(o instanceof ServiceDefinition)) return false;

        final ServiceDefinition that = (ServiceDefinition) o;

        if(id == null || that.getId() == null) return false;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
