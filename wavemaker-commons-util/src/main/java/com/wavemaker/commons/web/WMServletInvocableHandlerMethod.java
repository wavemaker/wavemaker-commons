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
