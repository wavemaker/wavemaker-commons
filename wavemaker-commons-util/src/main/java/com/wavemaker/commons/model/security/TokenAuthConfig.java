package com.wavemaker.commons.model.security;

/**
 * Created by arjuns on 21/2/17.
 */
public class TokenAuthConfig {
    private boolean enabled;
    private String key;
    private int tokenValiditySeconds;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
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
                ", key='" + key + '\'' +
                ", tokenValiditySeconds=" + tokenValiditySeconds +
                '}';
    }
}
