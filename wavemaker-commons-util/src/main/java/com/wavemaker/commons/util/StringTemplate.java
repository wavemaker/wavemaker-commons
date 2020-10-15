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
package com.wavemaker.commons.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility Class for formatting string with string as keys and optional values depending on keys.
 *
 * Eg: Typical case
 *
 * String:
 * a string with ${foo}
 * substitutions:
 * {"foo", "bar as replacement"}
 *
 * Generates as:
 * a string with bar as replacement
 *
 * Eg: Optionals case
 *
 * String:
 * ${foo[foo exists, with ${bar}]}
 * substitutions:
 * {"foo": "true", "bar": "replacement"}
 *
 * Generates as:
 * foo exists, with replacement
 *
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 19/1/16
 */
public class StringTemplate {
    private static final int KEY_GROUP = 2;
    private static final int OPTIONAL_VALUE_GROUP = 4;
    private static final Pattern KEY_PATTERN = Pattern.compile("\\$\\{(([^}\\[]+)(\\[([^\\]]+)\\])?)\\}");

    private final Matcher matcher;
    private final boolean blankIfNull;

    public StringTemplate(String template, boolean blankIfNull) {
        this.matcher = KEY_PATTERN.matcher(template);
        this.blankIfNull = blankIfNull;
    }


    public StringTemplate(String template) {
        this(template, false);
    }

    /**
     * @param map substitution map
     * @return substituted string
     */
    public String substitute(Map<String, ?> map) {
        synchronized (this.matcher) {
            this.matcher.reset();
            StringBuffer sb = new StringBuffer();
            while (this.matcher.find()) {
                String replacement = findReplacement(map);
                this.matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
            }
            this.matcher.appendTail(sb);
            return sb.toString();
        }
    }

    private String findReplacement(final Map<String, ?> map) {
        String keyWithPlaceHolder = this.matcher.group();
        String key = this.matcher.group(KEY_GROUP);
        String optionalValue = this.matcher.group(OPTIONAL_VALUE_GROUP);
        Object value = map.get(key);

        String replacement = "";
        if (value == null) {
            if (optionalValue == null) {
                replacement = (blankIfNull) ? "" : keyWithPlaceHolder;
            }
        } else {
            if (optionalValue == null) {
                replacement = value.toString();
            } else { // optional string exists, parsing again
                StringTemplate subStringTemplate = new StringTemplate(optionalValue, blankIfNull);
                replacement = subStringTemplate.substitute(map);
            }
        }
        return replacement;
    }
}
