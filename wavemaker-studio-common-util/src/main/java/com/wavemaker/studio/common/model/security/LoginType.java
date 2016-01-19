package com.wavemaker.studio.common.model.security;

public enum LoginType {
    DIALOG("dialog"),
    PAGE("page");

    private String name;

    LoginType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}