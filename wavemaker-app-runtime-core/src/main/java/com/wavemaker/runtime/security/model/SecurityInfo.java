/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
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
package com.wavemaker.runtime.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wavemaker.studio.common.model.security.LoginConfig;

/**
 * Created by ArjunSahasranam on 14/1/16.
 */
public class SecurityInfo {
    private boolean securityEnabled;
    private boolean authenticated;
    private LoginConfig loginConfig;
    private UserInfo userInfo;

    public boolean isSecurityEnabled() {
        return securityEnabled;
    }

    public void setSecurityEnabled(final boolean securityEnabled) {
        this.securityEnabled = securityEnabled;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(final boolean authenticated) {
        this.authenticated = authenticated;
    }

    @JsonProperty("login")
    public LoginConfig getLoginConfig() {
        return loginConfig;
    }

    public void setLoginConfig(final LoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(final UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "SecurityInfo{" +
                "securityEnabled=" + securityEnabled +
                ", authenticated=" + authenticated +
                ", login=" + loginConfig +
                ", userInfo=" + userInfo +
                '}';
    }
}
