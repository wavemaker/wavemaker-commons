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
public abstract class URLPattern {
    private final String patternString;

    public static URLPattern constructPattern(String requestPattern) {
        if (requestPattern.endsWith("/*")) {
            return new DirectoryURLPattern(requestPattern);
        } else if (requestPattern.startsWith("*.")) {
            return new ExtensionURLPattern(requestPattern);
        } else {
            return new ExactURLPattern(requestPattern);
        }
    }

    protected URLPattern(String pattern) {
        patternString = pattern;
    }

    public String getPatternString() {
        return patternString;
    }
    /**
     * This method, takes a request URI, and checks whether this uri matches this pattern or not. if matches will return
     * true. else returns false.
     *
     * @param requestURI
     * @return
     */
    public abstract boolean matches(String requestURI);
}

