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
package com.wavemaker.commons.pattern;

/**
 * Created by sunilp on 7/10/15.
 */
public class DirectoryURLPattern
    extends URLPattern {
    private String directoryPatternwithSlash;
    private String directoryPattern;

    DirectoryURLPattern(String pattern) {
        super(pattern);
        directoryPattern = pattern.substring(0, pattern.length() - 2);
        directoryPatternwithSlash = pattern.substring(0, pattern.length() - 1);
    }

    @Override
    public boolean matches(String requestURI) {
        if (requestURI == null || "".equals(requestURI)) {
            return false;
        }

        if (requestURI.length() == directoryPattern.length()) {
            if (directoryPattern.equals(requestURI)) {
                return true;
            }
        } else if (requestURI.startsWith(directoryPatternwithSlash)) {
            return true;
        }

        return false;
    }

    public String toString() {
        return "Directory-Pattern: [" + directoryPatternwithSlash + "*]";
    }
}

