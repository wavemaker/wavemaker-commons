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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * This class uses YamlProcessor overrides the DefaultYamlProcessor to convert the yaml resources to properties
 * with property values having List, Set etc objects instead of [0],[1] etc.
 * Example:
 * servers:
 * - dev.bar.com
 * - foo.bar.com
 * For the above yaml the generated property value will be a list of strings.
 */

public class WMYamlProcessor extends DefaultYamlProcessor {
    @Override
    protected void process(YamlProcessor.MatchCallback callback) {
        super.process((properties, map) -> {
            Map<String, Object> flattenedMap = getFlatMap(map);
            Properties customProperties = new Properties();
            customProperties.putAll(flattenedMap);
            callback.process(customProperties, map);
        });
    }

    private Map<String, Object> getFlatMap(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<>();
        buildFlatMap(result, source, null);
        return result;
    }

    private void buildFlatMap(Map<String, Object> result, Map<String, Object> source, @Nullable String path) {
        source.forEach((key, value) -> {
            if (StringUtils.hasText(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                } else {
                    key = path + '.' + key;
                }
            }
            if (value instanceof String) {
                result.put(key, value);
            } else if (value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                buildFlatMap(result, map, key);
            } else if (value instanceof Collection) {
                result.put(key, value);
            } else {
                result.put(key, (value != null ? value : ""));
            }
        });
    }
}
