/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.WMRuntimeException;

/**
 * @author Uday Shankar
 */
public class SSLUtils {

    private static final Logger logger = LoggerFactory.getLogger(SSLUtils.class);

    private static SSLContext allTrustedSSLContext;

    public static SSLContext getAllTrustedCertificateSSLContext() {
        if (allTrustedSSLContext == null) {

            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{NoCheckTrustManager.INSTANCE};
            // Install the all-trusting trust manager
            SSLContext sc;
            try {
                sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new SecureRandom());
                allTrustedSSLContext = sc;
            } catch (Exception e) {
                logger.warn("Failed in initialize ssl context", e);
                throw new WMRuntimeException("Failed in initialize ssl context",e);
            }
        }
        return allTrustedSSLContext;
    }

    public static class NoCheckTrustManager implements X509TrustManager {

        public static final NoCheckTrustManager INSTANCE = new NoCheckTrustManager();

        private NoCheckTrustManager() {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }
    }
}
