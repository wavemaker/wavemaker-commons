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

package com.wavemaker.runtime.data.sample.orahr;

// Generated Aug 19, 2007 9:06:50 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * JobHistoryId generated by hbm2java
 */
@SuppressWarnings({ "serial" })
public class JobHistoryId implements java.io.Serializable {

    private int employeeId;

    private Date startDate;

    public JobHistoryId() {
    }

    public JobHistoryId(int employeeId, Date startDate) {
        this.employeeId = employeeId;
        this.startDate = startDate;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof JobHistoryId)) {
            return false;
        }
        JobHistoryId castOther = (JobHistoryId) other;

        return this.getEmployeeId() == castOther.getEmployeeId()
            && (this.getStartDate() == castOther.getStartDate() || this.getStartDate() != null && castOther.getStartDate() != null
                && this.getStartDate().equals(castOther.getStartDate()));
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getEmployeeId();
        result = 37 * result + (getStartDate() == null ? 0 : this.getStartDate().hashCode());
        return result;
    }

}
