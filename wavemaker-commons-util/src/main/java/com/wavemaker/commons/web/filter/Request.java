/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
