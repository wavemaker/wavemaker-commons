package com.wavemaker.commons.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/8/18
 */
public class WMRestMethodArgsInterceptorConfiguration implements BeanFactoryAware {

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            final ConfigurableListableBeanFactory listableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
            final String[] namesForType = listableBeanFactory
                    .getBeanNamesForType(RequestMappingHandlerAdapter.class);

            for (final String beanName : namesForType) {
                final BeanDefinition definition = listableBeanFactory.getBeanDefinition(beanName);

                definition.setBeanClassName(WMRequestMappingHandlerAdapter.class.getName());
            }
        }
    }
}
