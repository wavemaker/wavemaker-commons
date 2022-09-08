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
package com.wavemaker.commons.core.web.rest;

import java.util.ArrayList;
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
    private List<String> parameters = new ArrayList<>();

    public ErrorResponse() {
    }

    public ErrorResponse(String messageKey, String message) {
        this.messageKey = messageKey;
        this.message = message;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public String getMessage() {
        return message;
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
