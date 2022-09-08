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
import java.io.InputStream;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

/**
 * Created by jvenugopal on 05-05-2015.
 */
public class EtagFilter extends ShallowEtagHeaderFilter {

    public static final String SKIP_ETAG = "skipEtag";
    private static final Logger etagFilterLogger = LoggerFactory.getLogger(EtagFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean clientSupportsEtag = doesClientSupportEtag(request);

        if (HttpMethod.GET.name().equals(request.getMethod()) && clientSupportsEtag) {
            etagFilterLogger
                    .debug("Setting Etag header for request for url {}, user-agent {}", request.getRequestURL(), request.getHeader("User-Agent"));
            response.setHeader("Cache-Control", "max-age=0"); // HTTP 1.1
            super.doFilterInternal(request, response, filterChain);
        } else {
            if (!clientSupportsEtag) {
                etagFilterLogger.debug("Client doesn't support Etag headers for request url {}, user-agent {}", request.getRequestURL(), request.getHeader("User-Agent"));
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
                response.setHeader("Pragma", "no-cache"); // HTTP 1.0
                response.setDateHeader("Expires", 0); // Proxies.    
            }
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Objects.equals(request.getAttribute(SKIP_ETAG), true);
    }

    @Override
    protected boolean isEligibleForEtag(HttpServletRequest request, HttpServletResponse response, int responseStatusCode, InputStream inputStream) {
        return (responseStatusCode >= 200 && responseStatusCode < 300) && HttpMethod.GET.name().equals(request.getMethod());
    }

    /**
     * To disable etag header for clients which doesn't support etag header.
     */
    protected boolean doesClientSupportEtag(HttpServletRequest request) {
        return true;
    }
}
