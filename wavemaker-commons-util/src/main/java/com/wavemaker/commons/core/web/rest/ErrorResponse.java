/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.core.web.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:jiteshmehta@pramati.com">Jitesh Mehta</a>
 * @version $Revision: 1.2 $, $Date: 2009/12/23 11:43:08 $, $Author: emrans $
 * @since Nov 20, 2008
 */
@XmlRootElement(name = "error")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private String messageKey;
    private String message;
    public ErrorResponse() {
    }   

    public ErrorResponse(String messageKey, String message) {
    	this.messageKey = messageKey;
    	this.message = message;
    }
    
    public String getMessageKey() {
        return messageKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
