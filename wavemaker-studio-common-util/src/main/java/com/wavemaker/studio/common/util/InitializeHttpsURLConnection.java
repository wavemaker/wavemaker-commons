package com.wavemaker.studio.common.util;

import javax.net.ssl.HttpsURLConnection;

/**
 * All HttpsURLConnection instances will use the initialized trust store unless over-ridden by per instance setter
 * method setSSLSocketFactory.
 *
 * Created by ArjunSahasranam on 7/7/16.
 */
public class InitializeHttpsURLConnection {
    static {
        // Sets the default SSLSocketFactory inherited by new instances of this class.
        HttpsURLConnection.setDefaultSSLSocketFactory(SSLUtils.getAllTrustedCertificateSSLContext().getSocketFactory());
    }
}
