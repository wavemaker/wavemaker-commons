package com.wavemaker.commons.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.wavemaker.commons.web.filter.Request;
import com.wavemaker.commons.web.filter.ServerTimingMetric;

/**
 * Created by srujant on 13/9/18.
 */
public class RequestTrackingResponseWrapper extends HttpServletResponseWrapper {

    private static final Logger logger = LoggerFactory.getLogger(RequestTrackingResponseWrapper.class);
    private static final String SERVER_TIMING_HEADER = "Server-Timing";

    private Request request;
    private boolean serverTimingHeaderFlag;

    public RequestTrackingResponseWrapper(HttpServletResponse response, Request request) {
        super(response);
        this.request = request;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        writeServerTimingResponseHeader();
        return super.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        writeServerTimingResponseHeader();
        return super.getWriter();
    }

    public void writeServerTimingResponseHeader() {
        if (!serverTimingHeaderFlag) {
            try {
                long endTime = System.currentTimeMillis();
                request.getServerTimingMetricList().add(0, new ServerTimingMetric("server", endTime - request.getStartTime(), null));
                setServerTimingResponseHeader();
            } catch (Exception e) {
                logger.warn("Failed to write Server-Timing header", e);
            }
            serverTimingHeaderFlag = true;
        }
    }

    private void setServerTimingResponseHeader() {
        List<ServerTimingMetric> serverTimingMetrics = request.getServerTimingMetricList();
        MultiValueMap<String, ServerTimingMetric> multiValueMap = new LinkedMultiValueMap<>();
        serverTimingMetrics.forEach(x->multiValueMap.add(x.getName(), x));
        String headerValue = multiValueMap.keySet().stream().map(subRequestScope -> {
            Collection<ServerTimingMetric> values = multiValueMap.get(subRequestScope);
            ServerTimingMetric toBeSavedServerTimingMetric = null;
            if (values.size() != 1) {
                long totalProcessingTime = 0;
                for (ServerTimingMetric serverTimingMetric : values) {
                    totalProcessingTime += serverTimingMetric.getProcessingTime();
                }
                toBeSavedServerTimingMetric = new ServerTimingMetric(subRequestScope, totalProcessingTime, values.size() + " times");
            } else {
                toBeSavedServerTimingMetric = values.iterator().next();
            }
            return toBeSavedServerTimingMetric.asString();
        }).collect(Collectors.joining(", "));
        logger.debug("Setting header ServerTiming with value {}", headerValue);
        addHeader(SERVER_TIMING_HEADER, headerValue);
    }

}
