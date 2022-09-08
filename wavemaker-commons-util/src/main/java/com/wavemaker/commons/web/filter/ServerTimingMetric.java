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

import java.util.Objects;

/**
 * @author Uday Shankar
 */
public class ServerTimingMetric {
    private String name;
    private Long processingTime;
    private String description;

    public ServerTimingMetric(String name, Long processingTime, String description) {
        this.name = name;
        this.processingTime = processingTime;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Long getProcessingTime() {
        return processingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ServerTimingMetric parse(String val) {
        if (val == null) {
            throw new IllegalArgumentException(val);
        }
        String s = val.trim();
        String[] strings = s.split(";");
        String name = null;
        Long processingTime = null;
        String description = null;
        for (String str : strings) {
            str = str.trim();
            String[] split = str.split("=");
            if (split.length == 1) {
                if (name != null) {
                    throw new IllegalArgumentException(val);
                }
                name = split[0].trim();
            } else if (split.length == 2) {
                String key = split[0].trim();
                String value = split[1].trim();
                if ("dur".equals(key)) {
                    processingTime = Long.parseLong(value);
                } else if ("desc".equals(key)) {
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        if (value.length() >= 2) {
                            value = value.substring(1, value.length() - 1);
                        } else {
                            throw new IllegalArgumentException(val);
                        }
                    }
                    description = value;
                }
            } else {
                throw new IllegalArgumentException(val);
            }
        }
        return new ServerTimingMetric(name, processingTime, description);
    }

    @Override
    public String toString() {
        return asString();
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (description != null) {
            sb.append("(").append(description).append(")");
        }
        if (processingTime != null) {
            sb.append(";dur=").append(processingTime);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerTimingMetric that = (ServerTimingMetric) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(processingTime, that.processingTime) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, processingTime, description);
    }
}
