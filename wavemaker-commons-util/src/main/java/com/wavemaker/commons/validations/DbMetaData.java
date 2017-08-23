package com.wavemaker.commons.validations;

import java.util.Map;

/**
 * Created by srujant on 17/8/17.
 */
public class DbMetaData {

    private Map<String, TableColumnMetaData> properties;

    public Map<String, TableColumnMetaData> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, TableColumnMetaData> properties) {
        this.properties = properties;
    }
}
