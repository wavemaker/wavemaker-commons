/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util;

import javax.net.ssl.HttpsURLConnection;

/**
 * All HttpsURLConnection instances will use the initialized trust store unless over-ridden by per instance setter
 * method setSSLSocketFactory.
 *
 * Created by ArjunSahasranam on 7/7/16.
 */
public class InitializeHttpsURLConnection {

    private InitializeHttpsURLConnection() {
    }

    static {
        // Sets the default SSLSocketFactory inherited by new instances of this class.
        HttpsURLConnection.setDefaultSSLSocketFactory(SSLUtils.getAllTrustedCertificateSSLContext().getSocketFactory());
    }
}
