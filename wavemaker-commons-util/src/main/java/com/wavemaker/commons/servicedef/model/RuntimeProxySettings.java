/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.servicedef.model;

/**
 * Created by kishorer on 26/7/16.
 */
public class RuntimeProxySettings {

    private boolean web;
    private boolean mobile;
    private boolean withCredentials;

    public RuntimeProxySettings() {
    }

    public RuntimeProxySettings(final boolean web, final boolean mobile, final boolean withCredentials) {
        this.web = web;
        this.mobile = mobile;
        this.withCredentials = withCredentials;
    }

    public boolean isWeb() {
        return web;
    }

    public void setWeb(boolean web) {
        this.web = web;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public boolean isWithCredentials() {
        return withCredentials;
    }

    public RuntimeProxySettings setWithCredentials(final boolean withCredentials) {
        this.withCredentials = withCredentials;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RuntimeProxySettings that = (RuntimeProxySettings) o;

        if (web != that.web) {
            return false;
        }
        return mobile == that.mobile;

    }

    @Override
    public int hashCode() {
        int result = (web ? 1 : 0);
        result = 31 * result + (mobile ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RuntimeProxySettings{" +
                "web=" + web +
                ", mobile=" + mobile +
                '}';
    }

}
