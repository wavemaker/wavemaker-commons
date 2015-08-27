/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.commons.core.web.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author <a href="mailto:jiteshmehta@pramati.com">Jitesh Mehta</a>
 * @version $Revision: 1.2 $, $Date: 2009/12/23 11:43:08 $, $Author: emrans $
 * @since Nov 20, 2008
 */
@XmlRootElement(name = "error")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private String id;
    private String messageKey;
    private String message;
    private List<String> parameters = new ArrayList<String>();

    public ErrorResponse() {
    }   

    public ErrorResponse(String id, String message) {
    	this.id = id;
    	this.message = message;
    }
    
    public ErrorResponse(String id, String messageKey, String message, String... values) {
        this.id = id;
        this.messageKey = messageKey;
        this.message = message;
        this.parameters = Arrays.asList(values);
    }

    public String getId() {
        return id;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public ErrorResponse addParameter(String parameter) {
        parameters.add(parameter);
        return this;
    }
}
