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
package com.wavemaker.commons.comparator;

import java.util.Comparator;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

/**
 * Works in tandem with {@link UrlComparator}. Sorts alphabetically url string by "grouping" it but does not disturb
 * the sorted order of {@link UrlComparator}
 * Created by ArjunSahasranam on 23/11/15.
 */
public abstract class UrlStringComparator<T> implements Comparator<T> {
    @Override
    public int compare(final T s1, final T s2) {
        final String urlPattern1 = getUrlPattern(s1);
        final String urlPattern2 = getUrlPattern(s2);
        if (StringUtils.isBlank(urlPattern1) && StringUtils.isBlank(urlPattern2)) {
            return 0;
        }
        if (StringUtils.isBlank(urlPattern1)) {
            return 1;
        }
        if (StringUtils.isBlank(urlPattern2)) {
            return -1;
        }
        final StringTokenizer s1Tokens = new StringTokenizer(urlPattern1, "/");
        final StringTokenizer s2Tokens = new StringTokenizer(urlPattern2, "/");
        if (s1Tokens.countTokens() > s2Tokens.countTokens()) {
            // s2Tokens.countTokens() indicates the number of times nextToken can be called. So, reducing count by 1.
            return compareTokens(s1Tokens, s2Tokens, s2Tokens.countTokens() - 1);
        } else {
            return compareTokens(s1Tokens, s2Tokens, s1Tokens.countTokens() - 1);
        }
    }

    /**
     * compares url tokens delimited by "/" till compareTo finds difference between tokens.
     */
    int compareTokens(StringTokenizer s1Tokens, StringTokenizer s2Tokens, int count) {
        for (int i = 0; i <= count; i++) {
            final String s1 = s1Tokens.nextToken();
            if (s1.equals("**")) {
                return 0;
            }
            final String s2 = s2Tokens.nextToken();
            if (s2.equals("**")) {
                return 0;
            }
            final int strCompare = s1.compareTo(s2);
            if (strCompare == 0) {
                continue;
            }
            return strCompare;
        }
        return 0;
    }

    public abstract String getUrlPattern(T t);
}
