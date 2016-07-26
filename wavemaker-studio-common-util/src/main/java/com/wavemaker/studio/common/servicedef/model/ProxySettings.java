package com.wavemaker.studio.common.servicedef.model;

/**
 * Created by kishorer on 26/7/16.
 */
public class ProxySettings {

    private boolean web;
    private boolean mobile;

    public ProxySettings() {
    }

    public ProxySettings(boolean web, boolean mobile) {
        this.web = web;
        this.mobile = mobile;
    }

    public boolean isWeb() {
        return web;
    }

    public void setWeb(boolean web) {
        this.web = web;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }
}
