package com.wavemaker.studio.common.proxy;

/**
 * Created by srujant on 26/12/16.
 */
public class AppProxyConstants {

    public static final String PROXY_PREFIX = "app";

    public static final String PROXY_ENABLED = "proxy.enabled";
    public static final String PROXY_HOST = "proxy.host";
    public static final String PROXY_PORT = "proxy.port";
    public static final String PROXY_USERNAME = "proxy.username";
    public static final String PROXY_PASSWORD = "proxy.password";


    public static final String APP_PROXY_ENABLED = getAppProxyKey(PROXY_ENABLED);
    public static final String APP_PROXY_HOST = getAppProxyKey(PROXY_HOST);
    public static final String APP_PROXY_PORT = getAppProxyKey(PROXY_PORT);
    public static final String APP_PROXY_USERNAME = getAppProxyKey(PROXY_USERNAME);
    public static final String APP_PROXY_PASSWORD = getAppProxyKey(PROXY_PASSWORD);

    private static String getAppProxyKey(String key) {
        return new StringBuilder(PROXY_PREFIX).append(".").append(key).toString();
    }

}
