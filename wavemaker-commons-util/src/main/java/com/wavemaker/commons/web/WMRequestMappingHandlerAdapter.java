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
