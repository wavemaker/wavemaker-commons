/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.studio.common.core.web.rest;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author <a href="mailto:jiteshmehta@pramati.com">Jitesh Mehta</a>
 * @version $Revision: 1.1 $, $Date: 2009/11/19 10:35:36 $, $Author: emrans $
 * @since Nov 20, 2008
 */
@XmlRootElement(name = "errors")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponses  {

    private List<ErrorResponse> error;

    private ErrorResponses() {
        this(Collections.<ErrorResponse> emptyList());
    }

    public ErrorResponses(List<ErrorResponse> errorResponses) {
        super();
        this.error = errorResponses;
    }

    public List<ErrorResponse> getError() {
        return error;
    }

    public void setError(List<ErrorResponse> error) {
        this.error = error;
    }

}
