package com.wavemaker.commons.model.security;

public class SessionConcurrencyConfig {

    private int maxSessionsAllowed;
    private boolean exceptionIfMaximumExceeded;

    public int getMaxSessionsAllowed() {
        return maxSessionsAllowed;
    }

    public void setMaxSessionsAllowed(int maxSessionAllowed) {
        this.maxSessionsAllowed = maxSessionsAllowed;
    }

    public boolean isExceptionIfMaximumExceeded() {
        return exceptionIfMaximumExceeded;
    }

    public void setExceptionIfMaximumExceeded(boolean exceptionIfMaximumExceeded) {
        this.exceptionIfMaximumExceeded = exceptionIfMaximumExceeded;
    }
}
