package com.wavemaker.commons.model.security;

public class SessionConcurrencyConfig {

    private int maxSessionsAllowed = -1;

    public int getMaxSessionsAllowed() {
        return maxSessionsAllowed;
    }

    public void setMaxSessionsAllowed(int maxSessionAllowed) {
        this.maxSessionsAllowed = maxSessionsAllowed;
    }

}
