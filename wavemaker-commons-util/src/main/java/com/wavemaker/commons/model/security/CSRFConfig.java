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
