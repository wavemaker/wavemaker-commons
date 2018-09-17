package com.wavemaker.commons.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.web.filter.Request;
import com.wavemaker.commons.web.filter.ServerTimingMetric;

/**
 * Created by srujant on 13/9/18.
 */
public class RequestTrackingResponseWrapper extends HttpServletResponseWrapper {

    private static final Logger logger = LoggerFactory.getLogger(RequestTrackingResponseWrapper.class);
    private static final String SERVER_TIMING_HEADER = "Server-Timing";

    private Request request;
    private boolean serverTimingHeaderFlag = false;
    private String requestTrackingIdPrefix;

    public RequestTrackingResponseWrapper(HttpServletResponse response, Request request, String requestTrackingIdPrefix) {
        super(response);
        this.request = request;
        this.requestTrackingIdPrefix = requestTrackingIdPrefix;
    }

    @Override
    public void flushBuffer() throws IOException {
        writeServerTimingResponseHeader();
        super.flushBuffer();
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

    @Override
    public void setContentType(String type) {
        super.setContentType(type);
        writeServerTimingResponseHeader();
    }

    @Override
    public void setContentLength(int len) {
        super.setContentLength(len);
        writeServerTimingResponseHeader();
    }


    public void writeServerTimingResponseHeader() {
        if (!serverTimingHeaderFlag) {
            long endTime = System.currentTimeMillis();
            request.getServerTimingMetricList().add(0, new ServerTimingMetric("Server", endTime - request.getStartTime(), null));
            setServerTimingResponseHeader();
            serverTimingHeaderFlag = true;
        }
    }

    private void setServerTimingResponseHeader() {
        StringBuilder sb = new StringBuilder();
        List<ServerTimingMetric> serverTimingMetrics = request.getServerTimingMetricList();
        
        MultiValuedMap<String, ServerTimingMetric> multiValueMap = new ArrayListValuedHashMap<>();
        serverTimingMetrics.forEach(x->multiValueMap.put(x.getSubRequestScope(), x));
        for (String subRequestScope : multiValueMap.keySet()) {
            long totalProcessingTime = 0;
            Collection<ServerTimingMetric> values = multiValueMap.get(subRequestScope);
            for (ServerTimingMetric serverTimingMetric : values) {
                totalProcessingTime+=serverTimingMetric.getProcessingTime();
            }
            sb.append(subRequestScope);
            sb.append("=").append(totalProcessingTime);
            if (values.size() !=1 ) {
                sb.append("\"Total Occurences=").append(values.size()).append("\"");
            } else {
                ServerTimingMetric serverTimingMetric = values.iterator().next();
                if (serverTimingMetric.getDescription() != null) {
                    sb.append(serverTimingMetric.getDescription());    
                }
            }
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        String serverTimings = sb.toString();
        logger.debug("Setting header ServerTiming with value {}", serverTimings);
        addHeader(SERVER_TIMING_HEADER, serverTimings);
    }

}
