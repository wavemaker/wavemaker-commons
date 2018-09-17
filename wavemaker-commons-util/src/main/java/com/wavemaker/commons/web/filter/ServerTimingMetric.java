package com.wavemaker.commons.web.filter;

/**
 * @author Uday Shankar
 */
public class ServerTimingMetric {
    private String subRequestScope;
    private long processingTime;
    private String description;

    public ServerTimingMetric(String subRequestScope, long processingTime, String description) {
        this.subRequestScope = subRequestScope;
        this.processingTime = processingTime;
        this.description = description;
    }

    public String getSubRequestScope() {
        return subRequestScope;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public String getDescription() {
        return description;
    }
}
