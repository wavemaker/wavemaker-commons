/*******************************************************************************
 * Copyright (C) 2024-2025 WaveMaker, Inc.
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
package com.wavemaker.commons.proxy;

/**
 * Created by srujant on 26/12/16.
 */
public final class AppPropertiesConstants {

    public static final String APP_PREFIX = "app";

    public static final String APP_PROXY_ENABLED = getAppPropertyKey("proxy.enabled");
    public static final String APP_PROXY_HOST = getAppPropertyKey("proxy.host");
    public static final String APP_PROXY_PORT = getAppPropertyKey("proxy.port");
    public static final String APP_PROXY_USERNAME = getAppPropertyKey("proxy.username");
    public static final String APP_PROXY_PASSWORD = getAppPropertyKey("proxy.password");
    public static final String APP_PROXY_INCLUDE_URLS = getAppPropertyKey("proxy.urls.include");
    public static final String APP_PROXY_EXCLUDE_URLS = getAppPropertyKey("proxy.urls.exclude");

    public static final String APP_COMPRESSION_ENABLED = getAppPropertyKey("web.compression.enabled");
    public static final String APP_COMPRESSION_MIN_SIZE = getAppPropertyKey("web.compression.minsize");
    public static final String APP_COMPRESSION_INCLUDE_TYPES = getAppPropertyKey("web.compression.mimetypes.includePatterns");
    public static final String APP_COMPRESSION_EXCLUDE_TYPES = getAppPropertyKey("web.compression.mimetypes.excludePatterns");

    public static final String APP_BUILD_UI_MODE = getAppPropertyKey("build.ui.mode");
    public static final String APP_BUILD_UI_SPA_CONFIG = getAppPropertyKey("build.ui.spa.enabled");
    public static final String APP_BUILD_UI_NG_CONFIG = getAppPropertyKey("build.ui.ng.config");
    public static final String APP_CDN_URL = getAppPropertyKey("cdnUrl");
    public static final String APP_API_URL = getAppPropertyKey("apiUrl");

    private static String getAppPropertyKey(String key) {
        return new StringBuilder(APP_PREFIX).append(".").append(key).toString();
    }

    private AppPropertiesConstants() {
    }

    public static final String DEFAULT_EXCLUDE_COMPRESSION_MIME_TYPES = "image/*";

}
