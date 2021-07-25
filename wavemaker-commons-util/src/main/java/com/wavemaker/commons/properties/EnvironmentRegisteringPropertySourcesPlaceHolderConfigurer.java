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

    private static final String APPLICATION_PROPERTY_SOURCE_NAME = "applicationProperties";

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
            MapPropertySource applicationPropertiesPropertySource = (MapPropertySource) propertySources.get(APPLICATION_PROPERTY_SOURCE_NAME);
            if (applicationPropertiesPropertySource == null) {
                applicationPropertiesPropertySource = new MapPropertySource(APPLICATION_PROPERTY_SOURCE_NAME, new HashMap<>());
                propertySources.addLast(applicationPropertiesPropertySource);
            }
            applicationPropertiesPropertySource.getSource().putAll(Maps.fromProperties(mergeProperties()));
            setPropertySources(propertySources);
        }
    }
}
