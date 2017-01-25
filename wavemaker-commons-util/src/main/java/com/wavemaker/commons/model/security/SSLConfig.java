package com.wavemaker.commons.model.security;

/**
 * Created by ArjunSahasranam on 20/6/16.
 */
public class SSLConfig {
    private int sslPort = 443;

    private boolean useSSL;

    private String excludedUrls;

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(final boolean useSSL) {
        this.useSSL = useSSL;
    }

    public int getSslPort() {
        return sslPort;
    }

    public void setSslPort(final int sslPort) {
        this.sslPort = sslPort;
    }

    public String getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(final String excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

    @Override
    public String toString() {
        return "SSLConfig{" +
                "sslPort='" + sslPort + '\'' +
                ", useSSL=" + useSSL +
                '}';
    }
}
