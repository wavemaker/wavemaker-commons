/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.wavemaker.commons.indexing.model;

import java.util.HashSet;
import java.util.Set;

import com.wavemaker.commons.indexing.model.databinding.DataBinding;

public class VariableResource {
    private String id;

    private String pageName;

    private String name;

    private String category;

    private String operationId;

    private String crudOperationId;

    private String owner;

    private String serviceType;
    private String serviceName;
    private DataBinding dataBinding;

    public Set<String> matchingFields = new HashSet<>();

    public Set<String> getMatchingFields() {
        return matchingFields;
    }

    private float scoreDoc;
    private int fieldScore;

    public VariableResource() {
    }

    public VariableResource(String id, String pageName, String name, String category, String operationId, String crudOperationId, String owner, String serviceType, String serviceName, DataBinding dataBinding, Set<String> matchingFields, float scoreDoc, int fieldScore) {
        this.id = id;
        this.pageName = pageName;
        this.name = name;
        this.category = category;
        this.operationId = operationId;
        this.crudOperationId = crudOperationId;
        this.owner = owner;
        this.serviceType = serviceType;
        this.serviceName = serviceName;
        this.dataBinding = dataBinding;
        this.matchingFields = matchingFields;
        this.scoreDoc = scoreDoc;
        this.fieldScore = fieldScore;
    }

    public void setScoreDoc(float scoreDoc) {
        this.scoreDoc = scoreDoc;
    }

    public float getScoreDoc() {
        return scoreDoc;
    }

    public int getFieldScore() {
        return fieldScore;
    }

    public void setFieldScore(int fieldScore) {
        this.fieldScore = fieldScore;
    }

    public void setMatchingFields(Set<String> matchingFields) {
        this.matchingFields = matchingFields;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getCrudOperationId() {
        return crudOperationId;
    }

    public void setCrudOperationId(String crudOperationId) {
        this.crudOperationId = crudOperationId;
    }

    public DataBinding getDataBinding() {
        return dataBinding;
    }

    public void setDataBinding(DataBinding dataBinding) {
        this.dataBinding = dataBinding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
