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

import java.util.Comparator;

import org.springframework.util.AntPathMatcher;

/**
 * Inspired from {@link AntPathMatcher.AntPatternComparator}.
 * Sorts from most specific to least specific.
 *
 * @author ArjunSahasranam on 5/11/15.
 */
public abstract class UrlComparator<T> implements Comparator<T> {

    @Override
    public int compare(final T o1, final T o2) {
        final String urlPattern1 = getUrlPattern(o1);
        final String urlPattern2 = getUrlPattern(o2);
        PatternInfo info1 = new PatternInfo(urlPattern1);
        PatternInfo info2 = new PatternInfo(urlPattern2);

        if (info1.isLeastSpecific() && info2.isLeastSpecific()) {
            return 0;
        } else if (info1.isLeastSpecific()) {
            return 1;
        } else if (info2.isLeastSpecific()) {
            return -1;
        }
        if (info1.isPrefixPattern() && info2.getDoubleWildcards() == 0) {
            return 1;
        } else if (info2.isPrefixPattern() && info1.getDoubleWildcards() == 0) {
            return -1;
        }

        if (info1.getTotalCount() != info2.getTotalCount()) {
            return info1.getTotalCount() - info2.getTotalCount();
        }

        if (info1.getLength() != info2.getLength()) {
            return info2.getLength() - info1.getLength();
        }

        if (info1.getSingleWildcards() < info2.getSingleWildcards()) {
            return -1;
        } else if (info2.getSingleWildcards() < info1.getSingleWildcards()) {
            return 1;
        }

        if (info1.getUriVars() < info2.getUriVars()) {
            return -1;
        } else if (info2.getUriVars() < info1.getUriVars()) {
            return 1;
        }
        return 0;
    }

    public abstract String getUrlPattern(T t);
}
