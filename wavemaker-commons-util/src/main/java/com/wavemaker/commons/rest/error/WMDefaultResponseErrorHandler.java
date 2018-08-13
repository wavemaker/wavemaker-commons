package com.wavemaker.commons.rest.error;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.wavemaker.commons.util.WMIOUtils;


/**
 * @author Uday Shankar
 */
public class WMDefaultResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(WMDefaultResponseErrorHandler.class);

    @Override
    protected byte[] getResponseBody(ClientHttpResponse response) {
        InputStream inputStream = null;
        try {
            inputStream = response.getBody();
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            logger.error("Failed to read error response", e);
        } finally {
            WMIOUtils.closeSilently(inputStream);
        }
        return new byte[0];
    }
}
