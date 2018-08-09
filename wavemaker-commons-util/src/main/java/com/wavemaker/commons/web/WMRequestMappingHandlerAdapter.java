package com.wavemaker.commons.web;

import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.wavemaker.commons.web.interceptor.RestMethodArgumentsInterceptor;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/8/18
 */
public class WMRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    @Autowired(required = false)
    private List<RestMethodArgumentsInterceptor> restMethodArgumentsInterceptors;

    @PostConstruct
    void init() {
        if (restMethodArgumentsInterceptors == null) {
            restMethodArgumentsInterceptors = Collections.emptyList();
        }
    }

    @Override
    protected ServletInvocableHandlerMethod createInvocableHandlerMethod(final HandlerMethod handlerMethod) {

        return new WMServletInvocableHandlerMethod(handlerMethod, restMethodArgumentsInterceptors);
    }
}
