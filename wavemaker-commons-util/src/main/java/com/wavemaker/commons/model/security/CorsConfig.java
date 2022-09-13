/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.model.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by srujant on 5/7/17.
 */
public class CorsConfig {
    @Value("${security.general.cors.enabled}")
    private boolean enabled;
    @Value("${security.general.cors.maxAge}")
    private long maxAge;
    @Value("${security.general.cors.allowCredentials}")
    private boolean allowCredentials;
    private List<PathEntry> pathEntries;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<PathEntry> getPathEntries() {
        return pathEntries;
    }

    public void setPathEntries(List<PathEntry> pathEntries) {
        this.pathEntries = pathEntries;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }
}
