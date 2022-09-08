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

package com.wavemaker.commons.properties;

import java.util.HashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import com.google.common.collect.Maps;

public class EnvironmentRegisteringPropertySourcesPlaceHolderConfigurer extends PropertySourcesPlaceholderConfigurer implements InitializingBean {

    private static final String DEFAULT_PROPERTY_SOURCE_NAME = "applicationProperties";

    private String propertySourceName = DEFAULT_PROPERTY_SOURCE_NAME;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        super.setEnvironment(environment);
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (environment instanceof ConfigurableEnvironment) {
            MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();
            MapPropertySource applicationPropertiesPropertySource = (MapPropertySource) propertySources.get(propertySourceName);
            if (applicationPropertiesPropertySource == null) {
                applicationPropertiesPropertySource = new MapPropertySource(propertySourceName, new HashMap<>());
                propertySources.addLast(applicationPropertiesPropertySource);
            }
            applicationPropertiesPropertySource.getSource().putAll(Maps.fromProperties(mergeProperties()));
            setPropertySources(propertySources);
        }
    }

    public void setPropertySourceName(final String propertySourceName) {
        this.propertySourceName = propertySourceName;
    }
}
