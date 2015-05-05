package com.wavemaker.studio.common.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

/**
 * Created by jvenugopal on 05-05-2015.
 */
public class EtagFilter extends ShallowEtagHeaderFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(HttpMethod.GET.name().equals(request.getMethod())){
            super.doFilterInternal(request, response, filterChain);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean isEligibleForEtag(HttpServletRequest request, HttpServletResponse response, int responseStatusCode, byte[] responseBody) {
        return (responseStatusCode >= 200 && responseStatusCode < 300)
                && HttpMethod.GET.name().equals(request.getMethod());
    }
}
