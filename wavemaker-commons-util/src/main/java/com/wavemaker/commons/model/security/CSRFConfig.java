/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
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
package com.wavemaker.commons.model.security;

/**
 * Created by kishorer on 7/7/16.
 */
public class CSRFConfig {

    private boolean enforceCsrfSecurity;
    private String headerName;

    public CSRFConfig() {
    }

    public boolean isEnforceCsrfSecurity() {
        return enforceCsrfSecurity;
    }

    public void setEnforceCsrfSecurity(final boolean enforceCsrfSecurity) {
        this.enforceCsrfSecurity = enforceCsrfSecurity;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(final String headerName) {
        this.headerName = headerName;
    }
}