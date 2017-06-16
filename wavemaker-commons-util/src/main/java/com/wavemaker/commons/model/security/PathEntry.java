package com.wavemaker.commons.model.security;


/**
 * Created by srujant on 5/7/17.
 */
public class PathEntry {
    private String name;
    private String path;
    private String allowedOrigins;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
