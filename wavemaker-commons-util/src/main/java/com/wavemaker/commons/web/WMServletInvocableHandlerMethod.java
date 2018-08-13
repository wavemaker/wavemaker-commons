package com.wavemaker.commons.web;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.wavemaker.commons.web.interceptor.RestMethodArgumentsInterceptor;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 1/8/18
 */
public class WMServletInvocableHandlerMethod extends ServletInvocableHandlerMethod {

    private List<RestMethodArgumentsInterceptor> interceptors;


    public WMServletInvocableHandlerMethod(
            final Object handler, final Method method,
            final List<RestMethodArgumentsInterceptor> interceptors) {
        super(handler, method);
        this.interceptors = interceptors;
    }

    public WMServletInvocableHandlerMethod(
            final HandlerMethod handlerMethod, final List<RestMethodArgumentsInterceptor> interceptors) {
        super(handlerMethod);
        this.interceptors = interceptors;
    }

    @Override
    protected Object doInvoke(final Object... arguments) throws Exception {
        Object[] args = arguments;

        if (getMethodParameters().length > 0 && !interceptors.isEmpty()) {
            for (final RestMethodArgumentsInterceptor interceptor : interceptors) {
                if (interceptor.supports(getMethod(), getMethodParameters())) {
                    args = interceptor.intercept(getMethod(), getMethodParameters(), args);
                }
            }

            if (logger.isTraceEnabled()) {
                logger.trace("Invoking '" + ClassUtils.getQualifiedMethodName(getMethod(), getBeanType()) +
                        "' with intercepted arguments " + Arrays.toString(args));
            }
        }

        return super.doInvoke(args);
    }

}
