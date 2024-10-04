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

package com.wavemaker.commons.io;

import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySourcesPropertyResolver;

import com.google.common.collect.Maps;

/**
 * PropertySourcesPropertyResolverOperation takes properties as input and uses PropertySourcesPropertyResolver to resolve the placeholders in the
 * resource. It also resolves the placeholders recursively.
 * Example:
 * rest.host=${default.host}
 * default.host=hostname
 * If the resource contains the placeholder ${rest.host} it will be resolved as hostname.
 */
public class PropertySourcesPropertyResolverOperation implements ResourceOperation<File> {

    private final PropertySourcesPropertyResolver propertyResolver;

    public PropertySourcesPropertyResolverOperation(Properties properties) {
        MapPropertySource mapPropertySource = new MapPropertySource("propertySource", new HashMap<>());
        mapPropertySource.getSource().putAll(Maps.fromProperties(properties));
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(mapPropertySource);
        propertyResolver = new PropertySourcesPropertyResolver(propertySources);
    }

    @Override
    public void perform(File resource) {
        String originalContent = resource.getContent().asString();
        String content = originalContent;
        content = propertyResolver.resolvePlaceholders(content);
        if (!Objects.equals(content, originalContent)) {
            resource.getContent().write(content);
        }
    }
}
