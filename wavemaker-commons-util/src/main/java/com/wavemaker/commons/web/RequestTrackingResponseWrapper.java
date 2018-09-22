package com.wavemaker.commons.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

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
    private boolean serverTimingHeaderFlag = false;

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
        StringBuilder sb = new StringBuilder();
        List<ServerTimingMetric> serverTimingMetrics = request.getServerTimingMetricList();
        
        MultiValueMap<String, ServerTimingMetric> multiValueMap = new LinkedMultiValueMap<>();
        serverTimingMetrics.forEach(x->multiValueMap.add(x.getSubRequestScope(), x));
        for (String subRequestScope : multiValueMap.keySet()) {
            long totalProcessingTime = 0;
            Collection<ServerTimingMetric> values = multiValueMap.get(subRequestScope);
            for (ServerTimingMetric serverTimingMetric : values) {
                totalProcessingTime+=serverTimingMetric.getProcessingTime();
            }
            sb.append(subRequestScope);
            sb.append("=").append(totalProcessingTime);
            if (values.size() !=1 ) {
                sb.append(";\"").append(values.size()).append(" times\"");
            } else {
                ServerTimingMetric serverTimingMetric = values.iterator().next();
                if (serverTimingMetric.getDescription() != null) {
                    sb.append(";").append(serverTimingMetric.getDescription());    
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
