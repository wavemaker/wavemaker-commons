/**
 * Copyright (C) 2014 WaveMaker, Inc. All rights reserved.
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
package com.wavemaker.runtime.data.sample.adventure;

// Generated Aug 18, 2007 5:20:14 PM by Hibernate Tools 3.2.0.b9

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ProductModel generated by hbm2java
 */
@SuppressWarnings({ "serial", "unchecked" })
public class ProductModel implements java.io.Serializable {

    private int productModelId;

    private String name;

    private Clob catalogDescription;

    private String rowguid;

    private Date modifiedDate;

    private Set products = new HashSet(0);

    private Set productModelProductDescriptions = new HashSet(0);

    public ProductModel() {
    }

    public ProductModel(int productModelId, String name, String rowguid, Date modifiedDate) {
        this.productModelId = productModelId;
        this.name = name;
        this.rowguid = rowguid;
        this.modifiedDate = modifiedDate;
    }

    public ProductModel(int productModelId, String name, Clob catalogDescription, String rowguid, Date modifiedDate, Set products,
        Set productModelProductDescriptions) {
        this.productModelId = productModelId;
        this.name = name;
        this.catalogDescription = catalogDescription;
        this.rowguid = rowguid;
        this.modifiedDate = modifiedDate;
        this.products = products;
        this.productModelProductDescriptions = productModelProductDescriptions;
    }

    public int getProductModelId() {
        return this.productModelId;
    }

    public void setProductModelId(int productModelId) {
        this.productModelId = productModelId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clob getCatalogDescription() {
        return this.catalogDescription;
    }

    public void setCatalogDescription(Clob catalogDescription) {
        this.catalogDescription = catalogDescription;
    }

    public String getRowguid() {
        return this.rowguid;
    }

    public void setRowguid(String rowguid) {
        this.rowguid = rowguid;
    }

    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set getProducts() {
        return this.products;
    }

    public void setProducts(Set products) {
        this.products = products;
    }

    public Set getProductModelProductDescriptions() {
        return this.productModelProductDescriptions;
    }

    public void setProductModelProductDescriptions(Set productModelProductDescriptions) {
        this.productModelProductDescriptions = productModelProductDescriptions;
    }

}
