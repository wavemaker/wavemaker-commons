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

package com.wavemaker.commons.indexing.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PageHTMLResource {
    private String pageName;

    private String widgetName;

    private String wmElementName;

    private Map<String, String> properties;

    private Map<String, String> events;

    public Set<String> matchingFields = new HashSet<>();
    private float scoreDoc;
    private int fieldScore;

    public PageHTMLResource() {
    }

    public PageHTMLResource(String pageName, String widgetName, String wmElementName, Map<String, String> properties, Map<String, String> events, Set<String> matchingFields, float scoreDoc, int fieldScore) {
        this.pageName = pageName;
        this.widgetName = widgetName;
        this.wmElementName = wmElementName;
        this.properties = properties;
        this.events = events;
        this.matchingFields = matchingFields;
        this.scoreDoc = scoreDoc;
        this.fieldScore = fieldScore;
    }

    public void setScoreDoc(float scoreDoc) {
        this.scoreDoc = scoreDoc;
    }

    public float getScoreDoc() {
        return scoreDoc;
    }

    public int getFieldScore() {
        return fieldScore;
    }

    public void setFieldScore(int fieldScore) {
        this.fieldScore = fieldScore;
    }

    public Set<String> getMatchingFields() {
        return matchingFields;
    }

    public void setMatchingFields(Set<String> matchingFields) {
        this.matchingFields = matchingFields;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public String getWmElementName() {
        return wmElementName;
    }

    public void setWmElementName(String wmElementName) {
        this.wmElementName = wmElementName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getEvents() {
        return events;
    }

    public void setEvents(Map<String, String> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "PageHTMLResource{" +
            "pageName='" + pageName + '\'' +
            ", widgetName='" + widgetName + '\'' +
            ", wmElementName='" + wmElementName + '\'' +
            ", properties=" + properties +
            ", events=" + events +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageHTMLResource that = (PageHTMLResource) o;
        return Objects.equals(pageName, that.pageName) && Objects.equals(widgetName, that.widgetName) && Objects.equals(wmElementName, that.wmElementName) && Objects.equals(properties, that.properties) && Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageName, widgetName, wmElementName, properties, events);
    }
}
