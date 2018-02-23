package com.wavemaker.commons.model.security;

/**
 * Created by srujant on 25/4/17.
 */
public class FrameOptions {

    private boolean enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "FrameOptions{" +
                "mode=" + mode +
                ", allowFromUrl='" + allowFromUrl + '\'' +
                '}';
    }

    public enum Mode {
        DENY, SAMEORIGIN, ALLOW_FROM;
    }
}
