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
package com.wavemaker.commons.model.security;

import javax.validation.constraints.NotNull;

/**
 * Created by kishorer on 6/7/16.
 */
public class XSSConfig {

    private boolean enforceXssSecurity;
    private String policyFile;
    @NotNull
    private XSSFilterStrategy xssFilterStrategy;
    @NotNull
    private XSSPolicyType policyType;
    private boolean dataBackwardCompatibility;
    @NotNull
    private XSSSanitizationLayer xssSanitizationLayer;

    public boolean isDataBackwardCompatibility() {
        return dataBackwardCompatibility;
    }

    public void setDataBackwardCompatibility(boolean dataBackwardCompatibility) {
        this.dataBackwardCompatibility = dataBackwardCompatibility;
    }

    public boolean isEnforceXssSecurity() {
        return enforceXssSecurity;
    }

    public void setEnforceXssSecurity(final boolean enforceXssSecurity) {
        this.enforceXssSecurity = enforceXssSecurity;
    }

    public XSSSanitizationLayer getXssSanitizationLayer() {
        return xssSanitizationLayer;
    }

    public void setXssSanitizationLayer(XSSSanitizationLayer xssSanitizationLayer) {
        this.xssSanitizationLayer = xssSanitizationLayer;
    }

    public String getPolicyFile() {
        return policyFile;
    }

    public void setPolicyFile(final String policyFile) {
        this.policyFile = policyFile;
    }

    public XSSFilterStrategy getXssFilterStrategy() {
        return xssFilterStrategy;
    }

    public void setXssFilterStrategy(final XSSFilterStrategy xssFilterStrategy) {
        this.xssFilterStrategy = xssFilterStrategy;
    }

    public XSSPolicyType getPolicyType() {
        return policyType;
    }

    public void setPolicyType(XSSPolicyType policyType) {
        this.policyType = policyType;
    }

    @Override
    public String toString() {
        return "XSSConfig{" +
            "enforceXssSecurity=" + enforceXssSecurity +
            ", policyFile='" + policyFile + '\'' +
            ", xssFilterStrategy=" + xssFilterStrategy +
            ", policyType=" + policyType +
            ", dataBackwardCompatibility=" + dataBackwardCompatibility +
            ", xssSanitizationLayer=" + xssSanitizationLayer +
            '}';
    }
}
