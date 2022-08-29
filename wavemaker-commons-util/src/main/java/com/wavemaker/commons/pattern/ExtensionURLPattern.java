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
package com.wavemaker.commons.pattern;

/**
 * Created by sunilp on 7/10/15.
 */
public class ExtensionURLPattern
        extends URLPattern
{
    private String extenstionPattern;

    ExtensionURLPattern(String pattern)
    {
        super(pattern);
        extenstionPattern = pattern.substring(1);
    }

    @Override
    public boolean matches(String requestURI)
    {
        if (requestURI == null || "".equals(requestURI)) {
            return false;
        }

        return requestURI.endsWith(extenstionPattern);
    }

    public String toString()
    {
        return "Extension-Pattern: [ " + getPatternString() + "]";
    }
}