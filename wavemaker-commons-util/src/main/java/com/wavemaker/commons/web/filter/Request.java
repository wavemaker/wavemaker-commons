package com.wavemaker.commons.web.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Uday Shankar
 */
public class Request {
    private String id;
    private int subRequestCounter = 1;
    private long startTime = System.currentTimeMillis();
    private String subRequestScope;
    private List<ServerTimingMetric> serverTimingMetricList = new ArrayList<>();

    public Request(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getAndIncrementSubRequestCounter() {
        return subRequestCounter++;
    }

    public List<ServerTimingMetric> getServerTimingMetricList() {
        return serverTimingMetricList;
    }

    public void addServerTimingMetric(ServerTimingMetric serverTimingMetric) {
        serverTimingMetricList.add(serverTimingMetric);
    }

    public long getStartTime() {
        return startTime;
    }

    public String getSubRequestScope() {
        return subRequestScope;
    }

    public void setSubRequestScope(String subRequestScope) {
        this.subRequestScope = subRequestScope;
    }
}
