package com.wavemaker.studio.common.model.security;

import java.util.regex.Pattern;


/**
 * Created by kishorer on 6/7/16.
 */
public class XSSConfig {

    public static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|HEAD|TRACE|OPTION)$");

    private boolean enforceXssSecurity;
    private String policyFile;
    private XSSFilterStrategy xssFilterStrategy;

    public XSSConfig() {
    }

    public boolean isEnforceXssSecurity() {
        return enforceXssSecurity;
    }

    public void setEnforceXssSecurity(final boolean enforceXssSecurity) {
        this.enforceXssSecurity = enforceXssSecurity;
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

    @Override
    public String toString() {
        return "XSSConfig{" +
                " enforceXssSecurity=" + enforceXssSecurity +
                ", policyFile='" + policyFile + '\'' +
                ", xssFilterStrategy=" + xssFilterStrategy +
                '}';
    }
}
