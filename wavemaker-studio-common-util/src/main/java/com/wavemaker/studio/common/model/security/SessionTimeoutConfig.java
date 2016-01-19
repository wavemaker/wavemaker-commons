package com.wavemaker.studio.common.model.security;

/**
 * Created by ArjunSahasranam on 18/1/16.
 */
public class SessionTimeoutConfig {
    private LoginType type;
    private String pageName;
    private int timeoutValue;

    public LoginType getType() {
        return type;
    }

    public void setType(final LoginType type) {
        this.type = type;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(final String pageName) {
        this.pageName = pageName;
    }

    public int getTimeoutValue() {
        return timeoutValue;
    }

    public void setTimeoutValue(final int timeoutValue) {
        this.timeoutValue = timeoutValue;
    }

    @Override
    public String toString() {
        return "SessionTimeoutConfig{" +
                "type=" + type +
                ", pageName='" + pageName + '\'' +
                ", timeoutValue=" + timeoutValue +
                '}';
    }
}
