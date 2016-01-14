package com.wavemaker.studio.common.model.security;

/**
 * Created by ArjunSahasranam on 13/1/16.
 */
public class LoginConfig {
    private LoginType type;
    private String pageName;
    private SessionTimeoutConfig sessionTimeout;

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

    public SessionTimeoutConfig getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(final SessionTimeoutConfig sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    @Override
    public String toString() {
        return "LoginConfig{" +
                "type=" + type +
                ", page='" + pageName + '\'' +
                ", sessionTimeout=" + sessionTimeout +
                '}';
    }
}
