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
package com.wavemaker.runtime.data.wmcloud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.wavemaker.runtime.data.util.JDBCUtils;
import com.wavemaker.studio.common.WMRuntimeException;
import com.wavemaker.studio.common.util.IOUtils;

/**
 *
 * @Author: Sowmya
 */
public class WavemakerCloudMySqlIntegration extends LocalSessionFactoryBean {
    /*
     * The test is the default database available in Cloud Mysql database which is running at 3306
     */
    private static final String SCHEMA_URL = "jdbc:mysql://localhost:3306/test";
    private String username;
    private String password;
    private String url;
    private String driverClassName;
    private String schemaName;

    public WavemakerCloudMySqlIntegration(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    private void ensureDBNameExists() throws WMRuntimeException {
        Connection con = null;
        Statement st = null;
        try{
            Class.forName(this.getDriverClassName());
            con = DriverManager.getConnection(this.SCHEMA_URL, this.getUsername(), this.getPassword());
            st = con.createStatement();
            st.executeUpdate(" CREATE DATABASE IF NOT EXISTS " + this.getSchemaName());
        }catch (Exception e){
            throw new WMRuntimeException("Failed to create Database "+this.getSchemaName() , e);
        }
        finally {
            IOUtils.closeByLogging(st);
            IOUtils.closeByLogging(con);
        }
    }


    @Override
    public void afterPropertiesSet() {
        if(!this.getDriverClassName().contains("mysql")){
            return;
        }
        String schemaNameExtract = JDBCUtils.getMySQLDatabaseName(this.getUrl());
        if(!StringUtils.isBlank(schemaNameExtract)) {
            setSchemaName(schemaNameExtract);
            ensureDBNameExists();
        }
    }

}
