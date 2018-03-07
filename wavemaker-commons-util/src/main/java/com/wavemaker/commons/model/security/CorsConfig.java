package com.wavemaker.commons.model.security;

import java.util.List;

/**
 * Created by srujant on 5/7/17.
 */
public class CorsConfig {
    private boolean enabled;
    private long maxAge;
    private boolean allowCredentials;
    private List<PathEntry> pathEntries;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<PathEntry> getPathEntries() {
        return pathEntries;
    }

    public void setPathEntries(List<PathEntry> pathEntries) {
        this.pathEntries = pathEntries;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }
}
