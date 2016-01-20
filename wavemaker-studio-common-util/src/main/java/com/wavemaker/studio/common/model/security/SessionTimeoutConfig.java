package com.wavemaker.studio.common.model.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final SessionTimeoutConfig that = (SessionTimeoutConfig) o;

        return new EqualsBuilder()
                .append(timeoutValue, that.timeoutValue)
                .append(type, that.type)
                .append(pageName, that.pageName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(pageName)
                .append(timeoutValue)
                .toHashCode();
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
