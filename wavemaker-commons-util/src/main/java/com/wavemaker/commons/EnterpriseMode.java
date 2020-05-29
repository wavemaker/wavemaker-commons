package com.wavemaker.commons;

public enum EnterpriseMode {
    OWN("OWN"), ORG("ORG");

    private String value;

    EnterpriseMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
