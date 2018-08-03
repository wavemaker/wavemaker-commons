package com.wavemaker.commons.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author Uday Shankar
 */
public class RequestTrackingFilter extends DelegatingFilterProxy {
    
    private String requestTrackingIdPrefix;

    private String requestTrackingHeaderName;

    public static final char REQUEST_ID_SEPARATOR = '-';
    
    private static ThreadLocal<Request> requestTrackingMap = new ThreadLocal<>();

    private static final Logger logger = LoggerFactory.getLogger(RequestTrackingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestTrackingId = httpServletRequest.getHeader(requestTrackingHeaderName);
        if (StringUtils.isBlank(requestTrackingId)) {
            requestTrackingId = generateNewRequestId();
            logger.debug("Request tracking id not set in header, setting new tracking id {}", requestTrackingId);
        }
        try {
            MDC.put(requestTrackingHeaderName, requestTrackingId);
            requestTrackingMap.set(new Request(requestTrackingId));
            httpServletResponse.setHeader(requestTrackingHeaderName, requestTrackingId);
            chain.doFilter(request, response);
        } finally {
            requestTrackingMap.remove();
            MDC.remove(requestTrackingHeaderName);
        }
    }

    @Override
    public void destroy() {
    }

    public String generateNewSubRequestId(String context) {
        Request currentRequest = requestTrackingMap.get();
        String subRequestId;
        if (currentRequest == null) {
            subRequestId = generateNewRequestId();
            logger.info("creating a new sub request id with value {} for context {}",
                    subRequestId, context);
        } else if (currentRequest.getRequestId().length() > 56) {
            subRequestId = generateNewRequestId();
            logger.warn("Current Request tracking id {} is too long, creating a new sub request id with value {} for context {} ", currentRequest.getRequestId(),
                    subRequestId, context);
        } else {
            StringBuilder sb = new StringBuilder(currentRequest.getRequestId()).append(REQUEST_ID_SEPARATOR);
            if (StringUtils.isNotBlank(context)) {
                sb.append(context);
                sb.append(".");
            }
            sb.append(currentRequest.getAndIncrementSubRequestCounter());
            subRequestId = sb.toString();
            logger.info("Creating new sub request tracking id {} for request context {}", subRequestId, context);
        }
        return subRequestId;
    }

    private String generateNewRequestId() {
        return requestTrackingIdPrefix + "." + RandomStringUtils.randomAlphanumeric(8);
    }

    public Runnable getRunnableInSubRequestScope(Runnable runnable) {
        final String subRequestId = generateNewSubRequestId("");
        return () -> {
            try {
                MDC.put(requestTrackingHeaderName, subRequestId);
                requestTrackingMap.set(new Request(subRequestId));
                runnable.run();
            } finally {
                requestTrackingMap.remove();
                MDC.remove(requestTrackingHeaderName);
            }
        };
    }

    public String getRequestTrackingIdPrefix() {
        return requestTrackingIdPrefix;
    }

    public void setRequestTrackingIdPrefix(String requestTrackingIdPrefix) {
        this.requestTrackingIdPrefix = requestTrackingIdPrefix;
    }

    public String getRequestTrackingHeaderName() {
        return requestTrackingHeaderName;
    }

    public void setRequestTrackingHeaderName(String requestTrackingHeaderName) {
        this.requestTrackingHeaderName = requestTrackingHeaderName;
    }
    
    public static class Request {
        private String requestId;
        private int subRequestCounter=1;

        public Request(String requestId) {
            this.requestId = requestId;
        }

        public String getRequestId() {
            return requestId;
        }

        public int getAndIncrementSubRequestCounter() {
            return subRequestCounter++;
        }
    }
}
