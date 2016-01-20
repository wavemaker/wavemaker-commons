package com.wavemaker.studio.common.model.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final LoginConfig that = (LoginConfig) o;

        return new EqualsBuilder()
                .append(type, that.type)
                .append(pageName, that.pageName)
                .append(sessionTimeout, that.sessionTimeout)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(pageName)
                .append(sessionTimeout)
                .toHashCode();
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
