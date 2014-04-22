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
package com.wavemaker.runtime.data.sample.orahr;

// Generated Aug 19, 2007 9:06:50 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Employees generated by hbm2java
 */
@SuppressWarnings({ "serial", "unchecked" })
public class Employees implements java.io.Serializable {

    private int employeeId;

    private Departments departments;

    private Employees employees;

    private Jobs jobs;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Date hireDate;

    private BigDecimal salary;

    private BigDecimal commissionPct;

    private Set jobHistories = new HashSet(0);

    private Set employeeses = new HashSet(0);

    private Set departmentses = new HashSet(0);

    public Employees() {
    }

    public Employees(int employeeId, Jobs jobs, String lastName, String email, Date hireDate) {
        this.employeeId = employeeId;
        this.jobs = jobs;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hireDate;
    }

    public Employees(int employeeId, Departments departments, Employees employees, Jobs jobs, String firstName, String lastName, String email,
        String phoneNumber, Date hireDate, BigDecimal salary, BigDecimal commissionPct, Set jobHistories, Set employeeses, Set departmentses) {
        this.employeeId = employeeId;
        this.departments = departments;
        this.employees = employees;
        this.jobs = jobs;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
        this.commissionPct = commissionPct;
        this.jobHistories = jobHistories;
        this.employeeses = employeeses;
        this.departmentses = departmentses;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Departments getDepartments() {
        return this.departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public Employees getEmployees() {
        return this.employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Jobs getJobs() {
        return this.jobs;
    }

    public void setJobs(Jobs jobs) {
        this.jobs = jobs;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getHireDate() {
        return this.hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getCommissionPct() {
        return this.commissionPct;
    }

    public void setCommissionPct(BigDecimal commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Set getJobHistories() {
        return this.jobHistories;
    }

    public void setJobHistories(Set jobHistories) {
        this.jobHistories = jobHistories;
    }

    public Set getEmployeeses() {
        return this.employeeses;
    }

    public void setEmployeeses(Set employeeses) {
        this.employeeses = employeeses;
    }

    public Set getDepartmentses() {
        return this.departmentses;
    }

    public void setDepartmentses(Set departmentses) {
        this.departmentses = departmentses;
    }

}
