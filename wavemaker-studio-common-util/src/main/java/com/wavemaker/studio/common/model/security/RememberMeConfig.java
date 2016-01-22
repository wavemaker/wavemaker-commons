package com.wavemaker.studio.common.model.security;

/**
 * Created by ArjunSahasranam on 22/1/16.
 */
public class RememberMeConfig {

    private boolean enabled;
    private long tokenValiditySeconds;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public long getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(final long tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    @Override
    public String toString() {
        return "RememberMeConfig{" +
                "enabled=" + enabled +
                ", tokenValiditySeconds=" + tokenValiditySeconds +
                '}';
    }
}
