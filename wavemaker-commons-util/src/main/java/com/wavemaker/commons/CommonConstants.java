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
package com.wavemaker.commons;

/**
 * @author Matt Small
 */
public abstract class CommonConstants {

    public static final String WM_SYSTEM_PROPERTY_PREFIX = "wm.";

    public static final String UTF8 = "UTF-8";

    //hibernate sql type codes
    public static final Integer DATE_TIME_WM_TYPE_CODE = -777;
    public static final Integer TIMESTAMP_WITH_TIMEZONE_SQL_CODE = -101;
    public static final Integer TIMESTAMP_WITH_LOCAL_TIMEZONE_SQL_CODE = -102;

    private CommonConstants() {
    }
}