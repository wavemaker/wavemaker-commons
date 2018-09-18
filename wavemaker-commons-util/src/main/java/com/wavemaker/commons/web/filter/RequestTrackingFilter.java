package com.wavemaker.commons.web.filter;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.wavemaker.commons.web.RequestTrackingResponseWrapper;

/**
 * @author Uday Shankar
 */
public class RequestTrackingFilter extends DelegatingFilterProxy {

    private String requestTrackingIdPrefix;

    private String requestTrackingHeaderName;
    
    private boolean serverTimingsEnabled = true;

    public static final char REQUEST_ID_SEPARATOR = '-';

    private static final String SERVER_TIMING_HEADER = "Server-Timing";

    private static ThreadLocal<Request> requestTrackingMap = new ThreadLocal<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestTrackingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestTrackingId = httpServletRequest.getHeader(requestTrackingHeaderName);
        if (StringUtils.isBlank(requestTrackingId)) {
            requestTrackingId = generateNewRequestId();
            LOGGER.debug("Request tracking id not set in header, setting new tracking id {}", requestTrackingId);
        }
        try {
            MDC.put(requestTrackingHeaderName, requestTrackingId);
            Request currentTrackingRequest = new Request(requestTrackingId);
            requestTrackingMap.set(currentTrackingRequest);
            httpServletResponse.setHeader(requestTrackingHeaderName, requestTrackingId);
            RequestTrackingResponseWrapper requestTrackingResponseWrapper = new RequestTrackingResponseWrapper((HttpServletResponse) response,
                    requestTrackingMap.get(), requestTrackingIdPrefix);
            chain.doFilter(request, requestTrackingResponseWrapper);
            requestTrackingResponseWrapper.writeServerTimingResponseHeader();
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
            LOGGER.info("creating a new sub request id with value {} for context {}",
                    subRequestId, context);
        } else if (currentRequest.getId().length() > 56) {
            subRequestId = generateNewRequestId();
            LOGGER.warn("Current Request tracking id {} is too long, creating a new sub request id with value {} for context {} ", currentRequest.getId(),
                    subRequestId, context);
        } else {
            StringBuilder sb = new StringBuilder(currentRequest.getId()).append(REQUEST_ID_SEPARATOR);
            sb.append(String.format("%02d", currentRequest.getAndIncrementSubRequestCounter()));
            if (StringUtils.isNotBlank(context)) {
                sb.append(context);
            }
            subRequestId = sb.toString();
            LOGGER.info("Creating new sub request tracking id {} for request context {}", subRequestId, context);
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

    public <T> T executeInSubRequestScope(String context, Function<String, T> function) {
        String subRequestScopeId = generateNewSubRequestId(context);
        if (serverTimingsEnabled) {
            Request request = requestTrackingMap.get();
            if (request != null) {
                long startTime = System.currentTimeMillis();
                String previousSubRequestScope = request.getSubRequestScope();
                String subRequestScope = subRequestScopeId.substring(request.getId().length() + 1);
                try {
                    request.setSubRequestScope(subRequestScope);
                    T returnValue = function.apply(subRequestScopeId);
                    return returnValue;
                } finally {
                    request.setSubRequestScope(previousSubRequestScope);
                    long processingTime = System.currentTimeMillis() - startTime;
                    addServerTimingMetrics(subRequestScope+"client", processingTime, null);
                }
            }
        }
        return function.apply(subRequestScopeId);
    }

    public void addServerTimingMetricsForSubRequest(HttpHeaders httpHeaders) {
        if (serverTimingsEnabled) {
            Request request = requestTrackingMap.get();
            if (request != null) {
                if (StringUtils.isNotBlank(request.getSubRequestScope())) {
                    List<String> serverTimings = httpHeaders.get(SERVER_TIMING_HEADER);
                    if (CollectionUtils.isNotEmpty(serverTimings)) {
                        for (String serverTiming : serverTimings) {
                            String[] serverTimingEntries = serverTiming.split(", ");
                            for (String serverTimingEntry : serverTimingEntries) {
                                String[] entry = serverTimingEntry.split(";");
                                String[] split = entry[0].split("=");
                                String description = null;
                                if (entry.length > 1) {
                                    description = entry[2];
                                }
                                addServerTimingMetrics(request.getSubRequestScope() + REQUEST_ID_SEPARATOR + split[0], Long.valueOf(split[1]), description);
                            }
                        }
                    }
                }
            }
        }
    }

    public void addServerTimingMetrics(String subRequestScope, long processingTime, String description) {
        if (serverTimingsEnabled) {
            Request request = requestTrackingMap.get();
            if (request != null) {
                request.addServerTimingMetric(new ServerTimingMetric(subRequestScope, processingTime, description));
            }
        }
    }
}
