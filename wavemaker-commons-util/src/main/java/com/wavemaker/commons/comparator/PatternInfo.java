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
package com.wavemaker.commons.comparator;

import java.util.regex.Pattern;

import org.springframework.util.AntPathMatcher;

/**
 * Inspired from {@link AntPathMatcher.AntPatternComparator}
 *
 * @author ArjunSahasranam on 5/11/15.
 */
class PatternInfo {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{[^/]+?\\}");
    private final String pattern;

    private int uriVars;

    private int singleWildcards;

    private int doubleWildcards;

    private boolean catchAllPattern;

    private boolean prefixPattern;

    private Integer length;

    public PatternInfo(String pattern) {
        this.pattern = pattern;
        if (this.pattern != null) {
            initCounters();
            this.catchAllPattern = this.pattern.equals("/**") || this.pattern.equals("/");
            this.prefixPattern = !this.catchAllPattern && this.pattern.endsWith("/**");
        }
        if (this.uriVars == 0) {
            this.length = (this.pattern != null ? this.pattern.length() : 0);
        }
    }

    protected void initCounters() {
        int pos = 0;
        while (pos < this.pattern.length()) {
            if (this.pattern.charAt(pos) == '{') {
                this.uriVars++;
                pos++;
            } else if (this.pattern.charAt(pos) == '*') {
                if (pos + 1 < this.pattern.length() && this.pattern.charAt(pos + 1) == '*') {
                    this.doubleWildcards++;
                    pos += 2;
                } else if (pos > 0 && !this.pattern.substring(pos - 1).equals(".*")) {
                    this.singleWildcards++;
                    pos++;
                } else {
                    pos++;
                }
            } else {
                pos++;
            }
        }
    }

    public int getUriVars() {
        return this.uriVars;
    }

    public int getSingleWildcards() {
        return this.singleWildcards;
    }

    public int getDoubleWildcards() {
        return this.doubleWildcards;
    }

    public boolean isLeastSpecific() {
        return (this.pattern == null || this.catchAllPattern);
    }

    public boolean isPrefixPattern() {
        return this.prefixPattern;
    }

    public int getTotalCount() {
        return this.uriVars + this.singleWildcards + (2 * this.doubleWildcards);
    }

    /**
     * Returns the length of the given pattern, where template variables are considered to be 1 long.
     */
    public int getLength() {
        if (this.length == null) {
            this.length = VARIABLE_PATTERN.matcher(this.pattern).replaceAll("#").length();
        }
        return this.length;
    }
}

