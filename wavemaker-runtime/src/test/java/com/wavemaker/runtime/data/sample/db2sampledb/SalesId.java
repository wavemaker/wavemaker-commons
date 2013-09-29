/*
 *  Copyright (C) 2009 WaveMaker Software, Inc.
 *
 *  This file is part of the WaveMaker Server Runtime.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wavemaker.runtime.data.sample.db2sampledb;

// Generated Feb 7, 2008 1:47:51 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;

/**
 * SalesId generated by hbm2java
 */
@SuppressWarnings({ "serial" })
public class SalesId implements java.io.Serializable {

    private Date salesDate;

    private String salesPerson;

    private String region;

    private Integer sales;

    public SalesId() {
    }

    public SalesId(Date salesDate, String salesPerson, String region, Integer sales) {
        this.salesDate = salesDate;
        this.salesPerson = salesPerson;
        this.region = region;
        this.sales = sales;
    }

    public Date getSalesDate() {
        return this.salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public String getSalesPerson() {
        return this.salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getSales() {
        return this.sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof SalesId)) {
            return false;
        }
        SalesId castOther = (SalesId) other;

        return (this.getSalesDate() == castOther.getSalesDate() || this.getSalesDate() != null && castOther.getSalesDate() != null
            && this.getSalesDate().equals(castOther.getSalesDate()))
            && (this.getSalesPerson() == castOther.getSalesPerson() || this.getSalesPerson() != null && castOther.getSalesPerson() != null
                && this.getSalesPerson().equals(castOther.getSalesPerson()))
            && (this.getRegion() == castOther.getRegion() || this.getRegion() != null && castOther.getRegion() != null
                && this.getRegion().equals(castOther.getRegion()))
            && (this.getSales() == castOther.getSales() || this.getSales() != null && castOther.getSales() != null
                && this.getSales().equals(castOther.getSales()));
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + (getSalesDate() == null ? 0 : this.getSalesDate().hashCode());
        result = 37 * result + (getSalesPerson() == null ? 0 : this.getSalesPerson().hashCode());
        result = 37 * result + (getRegion() == null ? 0 : this.getRegion().hashCode());
        result = 37 * result + (getSales() == null ? 0 : this.getSales().hashCode());
        return result;
    }

}
