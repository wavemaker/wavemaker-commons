/**
 * Copyright © 2015 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.studio.common.web.filter;

import java.io.IOException;
import java.io.InputStream;

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

    private static final Logger logger = LoggerFactory.getLogger(EtagFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Disabling etag for ie browser as it is not honouring etag header.
        boolean requestedFromIeBrowser = isRequestFromIeBrowser(request);

        if(request.getRequestURL().indexOf("/services") != -1){
            logger.debug("Etag Request for url {}, IE browser {}, user-agent {}, servlet Path {} ", request.getRequestURL(), requestedFromIeBrowser, request.getHeader("User-Agent"), request.getServletPath());
        }

        //Setting no cache for ie as etag is disabled for it.
        if(requestedFromIeBrowser && (request.getServletPath().startsWith("/services") || request.getServletPath().startsWith("/app") || request.getServletPath().startsWith("/pages") || request.getServletPath().endsWith(".json"))){
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0
            response.setDateHeader("Expires", 0); // Proxies.
        }
        if(HttpMethod.GET.name().equals(request.getMethod()) && !requestedFromIeBrowser){
            response.setHeader("Cache-Control", "max-age=0"); // HTTP 1.1
            super.doFilterInternal(request, response, filterChain);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    protected boolean isEligibleForEtag(HttpServletRequest request, HttpServletResponse response, int responseStatusCode, InputStream inputStream) {
        return (responseStatusCode >= 200 && responseStatusCode < 300)
                && HttpMethod.GET.name().equals(request.getMethod());
    }

    private boolean isRequestFromIeBrowser(HttpServletRequest request) {
        return request.getHeader("User-Agent") != null && (request.getHeader("User-Agent").contains("Trident") || request.getHeader("User-Agent").contains("MSIE"));
    }
}
