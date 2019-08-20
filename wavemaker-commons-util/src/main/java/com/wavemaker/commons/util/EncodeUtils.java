package com.wavemaker.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.wavemaker.commons.WMRuntimeException;

/**
 * @author Kishore Routhu on 20/3/18 12:07 PM.
 */
public class EncodeUtils {

    private EncodeUtils() {
    }

    public static String encode(String content) {
        try {
            return URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new WMRuntimeException(e);
        }
    }

    public static String decode(String content) {
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new WMRuntimeException(e);
        }
    }
}
