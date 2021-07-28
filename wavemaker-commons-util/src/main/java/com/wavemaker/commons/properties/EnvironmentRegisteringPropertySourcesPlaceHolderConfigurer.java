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
