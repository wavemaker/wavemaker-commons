/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.model.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by ArjunSahasranam on 18/1/16.
 */
public class SessionTimeoutConfig {
    private LoginType type;
    private String pageName;
    private int timeoutValue;
    private boolean concurrentSession ;
    private boolean exceptionIfMaximumExceeded ;
    private int maximumSessions ;

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

    public boolean isConcurrentSession() {
        return concurrentSession;
    }

    public void setConcurrentSession(boolean concurrentSession) {
        this.concurrentSession = concurrentSession;
    }

    public boolean isExceptionIfMaximumExceeded() {
        return exceptionIfMaximumExceeded;
    }

    public void setExceptionIfMaximumExceeded(boolean exceptionIfMaximumExceeded) {
        this.exceptionIfMaximumExceeded = exceptionIfMaximumExceeded;
    }

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
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
                ", concurrentSession=" + concurrentSession +
                ", exceptionIfMaximumExceeded=" + exceptionIfMaximumExceeded +
                ", maximumSessions=" + maximumSessions +
                '}';
    }
}
