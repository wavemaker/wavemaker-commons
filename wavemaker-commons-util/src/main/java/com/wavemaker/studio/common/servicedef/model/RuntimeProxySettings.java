package com.wavemaker.studio.common.servicedef.model;

/**
 * Created by kishorer on 26/7/16.
 */
public class RuntimeProxySettings {

    private boolean web;
    private boolean mobile;

    public RuntimeProxySettings() {
    }

    public RuntimeProxySettings(boolean web, boolean mobile) {
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
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RuntimeProxySettings that = (RuntimeProxySettings) o;

        if (web != that.web) return false;
        return mobile == that.mobile;

    }

    @Override
    public int hashCode() {
        int result = (web ? 1 : 0);
        result = 31 * result + (mobile ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RuntimeProxySettings{" +
                "web=" + web +
                ", mobile=" + mobile +
                '}';
    }

}
