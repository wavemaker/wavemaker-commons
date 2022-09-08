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
package com.wavemaker.commons.util;

import org.apache.commons.lang3.StringUtils;

import com.wavemaker.commons.WMRuntimeException;

/**
 * Utility methods that work with Class instances (see ObjectUtils, as well).
 * 
 * @author Matt Small
 */
public class ClassUtils {

    public static Object newInstance(Class<?> c) {
        try {
            return c.newInstance();
        } catch (InstantiationException ex) {
            String s = ex.getMessage();
            if (s == null || s.equals("")) {
                s = "Failed to instantiate " + c.getName();
            }
            throw new WMRuntimeException(s, ex);
        } catch (IllegalAccessException ex) {
            throw new WMRuntimeException(ex);
        }
    }

    public static String getPropertyGetterName(String propertyName) {
        return "get" + StringUtils.capitalize(propertyName);
    }

    public static String getAltPropertyGetterName(String propertyName) {
        return "is" + StringUtils.capitalize(propertyName);
    }

    public static String getPropertySetterName(String propertyName) {
        return "set" + StringUtils.capitalize(propertyName);
    }

    private ClassUtils() {
    }
}