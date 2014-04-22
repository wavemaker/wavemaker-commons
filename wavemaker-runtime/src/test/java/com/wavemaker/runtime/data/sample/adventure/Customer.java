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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Customer generated by hbm2java
 */
@SuppressWarnings({ "serial", "unchecked" })
public class Customer implements java.io.Serializable {

    private int customerId;

    private boolean nameStyle;

    private String title;

    private String firstName;

    private String middleName;

    private String lastName;

    private String suffix;

    private String companyName;

    private String salesPerson;

    private String emailAddress;

    private String phone;

    private String passwordHash;

    private String passwordSalt;

    private String rowguid;

    private Date modifiedDate;

    private Set salesOrderHeaders = new HashSet(0);

    private Set customerAddresses = new HashSet(0);

    public Customer() {
    }

    public Customer(int customerId, boolean nameStyle, String firstName, String lastName, String passwordHash, String passwordSalt, String rowguid,
        Date modifiedDate) {
        this.customerId = customerId;
        this.nameStyle = nameStyle;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.rowguid = rowguid;
        this.modifiedDate = modifiedDate;
    }

    public Customer(int customerId, boolean nameStyle, String title, String firstName, String middleName, String lastName, String suffix,
        String companyName, String salesPerson, String emailAddress, String phone, String passwordHash, String passwordSalt, String rowguid,
        Date modifiedDate, Set salesOrderHeaders, Set customerAddresses) {
        this.customerId = customerId;
        this.nameStyle = nameStyle;
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
        this.companyName = companyName;
        this.salesPerson = salesPerson;
        this.emailAddress = emailAddress;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.rowguid = rowguid;
        this.modifiedDate = modifiedDate;
        this.salesOrderHeaders = salesOrderHeaders;
        this.customerAddresses = customerAddresses;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isNameStyle() {
        return this.nameStyle;
    }

    public void setNameStyle(boolean nameStyle) {
        this.nameStyle = nameStyle;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSalesPerson() {
        return this.salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return this.passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
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

    public Set getSalesOrderHeaders() {
        return this.salesOrderHeaders;
    }

    public void setSalesOrderHeaders(Set salesOrderHeaders) {
        this.salesOrderHeaders = salesOrderHeaders;
    }

    public Set getCustomerAddresses() {
        return this.customerAddresses;
    }

    public void setCustomerAddresses(Set customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

}
