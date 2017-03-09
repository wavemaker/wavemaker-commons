package com.wavemaker.commons.model.security;

/**
 * Created by arjuns on 21/2/17.
 */
public class TokenAuthConfig {
    private boolean enabled;
    private String parameter;
    private int tokenValiditySeconds;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(final String parameter) {
        this.parameter = parameter;
    }

    public int getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(final int tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    @Override
    public String toString() {
        return "TokenAuthConfig{" +
                "enabled=" + enabled +
                ", parameter='" + parameter + '\'' +
                ", tokenValiditySeconds=" + tokenValiditySeconds +
                '}';
    }
}
