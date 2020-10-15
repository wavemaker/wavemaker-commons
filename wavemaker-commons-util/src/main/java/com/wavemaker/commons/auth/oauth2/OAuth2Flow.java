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
package com.wavemaker.commons.auth.oauth2;

public enum OAuth2Flow {
    AUTHORIZATION_CODE("accessCode"),
    IMPLICIT("implicit");

    private String value;

    OAuth2Flow(String value) {
        this.value = value;
    }

    public static OAuth2Flow fromValue(String flow) {
        if (flow != null) {
            for (OAuth2Flow oAuthFlow : values()) {
                if (flow.equals(oAuthFlow.toString()) || flow.equals(oAuthFlow.value)) {
                    return oAuthFlow;
                }
            }
        }
        return OAuth2Flow.valueOf(flow);
    }
}
