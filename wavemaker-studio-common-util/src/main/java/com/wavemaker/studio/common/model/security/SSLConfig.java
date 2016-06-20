package com.wavemaker.studio.common.model.security;

/**
 * Created by ArjunSahasranam on 20/6/16.
 */
public class SSLConfig {
    private String sslPort = "443";

    private boolean useSSL;

    public String getSslPort() {
        return sslPort;
    }

    public void setSslPort(final String sslPort) {
        this.sslPort = sslPort;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(final boolean useSSL) {
        this.useSSL = useSSL;
    }

    @Override
    public String toString() {
        return "SSLConfig{" +
                "sslPort='" + sslPort + '\'' +
                ", useSSL=" + useSSL +
                '}';
    }
}
