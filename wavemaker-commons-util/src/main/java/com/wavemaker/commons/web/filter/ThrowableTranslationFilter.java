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
package com.wavemaker.commons.web.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.util.HttpRequestUtils;

/**
 * @author Uday Shankar
 */
public class ThrowableTranslationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ThrowableTranslationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            chain.doFilter(request, response);
        } catch (Throwable e) {
            logger.error("Error occurred while serving the request with url {}", httpServletRequest.getRequestURI(), e);
            if (!response.isCommitted()) {
                HttpRequestUtils.writeJsonErrorResponse("Internal Server Error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, httpServletResponse);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
