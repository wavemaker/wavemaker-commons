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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesToMapConverter {

    /**
     * Converts the given properties to a nested map.
     * Example: if the properties are as follows
     * security.general.ssl.enabled=true
     * security.general.ssl.port=8443
     *
     * The constructed map will be as follows
     * security:
     *  general:
     *    ssl:
     *     enabled: true
     *     port: 8443
     * <pre>
     * @param properties
     * @return a nested map
     */
    public static Map<String, Object> propertiesToMapConverter(Properties properties) {
        Map<String, Object> map = new LinkedHashMap<>();
        properties.keySet().forEach(key -> {
            String[] propertyKeyArray = key.toString().split("\\.");
            Map<String, Object> nestedMap = map;
            for (int index = 0; index < propertyKeyArray.length - 1; index++) {
                nestedMap = (Map<String, Object>) nestedMap.computeIfAbsent(propertyKeyArray[index], k -> new LinkedHashMap<>());
            }
            nestedMap.put(propertyKeyArray[propertyKeyArray.length - 1], properties.get(key.toString()));
        });
        return map;
    }
}
