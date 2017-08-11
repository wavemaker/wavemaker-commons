package com.wavemaker.commons.oauth;

/**
 * Created by srujant on 11/8/17.
 */
public class Scope {
    private String name;
    private String value;

    public Scope() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
