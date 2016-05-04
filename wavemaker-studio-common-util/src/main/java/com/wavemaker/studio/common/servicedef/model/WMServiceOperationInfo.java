package com.wavemaker.studio.common.servicedef.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 27/11/15
 */
public class WMServiceOperationInfo {

    private String name;
    private String methodType;
    private String relativePath;
    private String httpMethod;
    private List<Parameter> parameters;
    private List<String> produces;
    private List<String> consumes;
    private String authorization;

    @JsonIgnore
    public static synchronized WMServiceOperationInfo getNewInstance() {
        return new WMServiceOperationInfo();
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public WMServiceOperationInfo addRelativePath(String relativePath) {
        this.relativePath = relativePath;
        return this;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public WMServiceOperationInfo addHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WMServiceOperationInfo addName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    public WMServiceOperationInfo addConsumes(List<String> consumes) {
        this.consumes = consumes;
        return this;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public WMServiceOperationInfo addParameters(List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public WMServiceOperationInfo addMethodType(String methodType) {
        this.methodType = methodType;
        return this;
    }

    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(final List<String> produces) {
        this.produces = produces;
    }

    public WMServiceOperationInfo addProduces(final List<String> produces) {
        this.produces = produces;
        return this;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(final String authorization) {
        this.authorization = authorization;
    }

    public WMServiceOperationInfo addAuthorization(final String authorization) {
        this.authorization = authorization;
        return this;
    }

    @Override
    public String toString() {
        return "WMServiceOperationInfo{" +
                "relativePath='" + relativePath + '\'' +
                ", methodType='" + methodType + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", name='" + name + '\'' +
                ", authorization='" + authorization + '\'' +
                ", produces=" + produces +
                ", consumes=" + consumes +
                ", parameters=" + parameters +
                '}';
    }
}
