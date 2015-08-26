package com.wavemaker.studio.common.web.filter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

/**
 * Created by jvenugopal on 05-05-2015.
 */
public class EtagFilter extends ShallowEtagHeaderFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Disabling etag for ie browser as it is not honouring etag header.
        boolean requestedFromIeBrowser = isRequestFromIeBrowser(request);
        //Setting no cache for ie as etag is disabled for it.
        if(requestedFromIeBrowser && request.getServletPath().startsWith("/services")){
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0
            response.setDateHeader("Expires", 0); // Proxies.
        }
        if(HttpMethod.GET.name().equals(request.getMethod()) && !requestedFromIeBrowser){
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
        return request.getHeader("User-Agent") != null && request.getHeader("User-Agent").contains("Trident");
    }
}
