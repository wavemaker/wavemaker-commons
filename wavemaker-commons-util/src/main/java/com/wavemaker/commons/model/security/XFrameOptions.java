package com.wavemaker.commons.model.security;

/**
 * Created by srujant on 25/4/17.
 */
public class XFrameOptions {


    private Mode mode;
    private String allowFromUrl;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getAllowFromUrl() {
        return allowFromUrl;
    }

    public void setAllowFromUrl(String allowFromUrl) {
        this.allowFromUrl = allowFromUrl;
    }

    @Override
    public String toString() {
        return "XFrameOptions{" +
                "mode=" + mode +
                ", allowFromUrl='" + allowFromUrl + '\'' +
                '}';
    }

    public enum Mode {

        DENY, SAMEORIGIN, ALLOW_FROM;

    }
}
