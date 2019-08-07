package com.wavemaker.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.wavemaker.commons.WMRuntimeException;

/**
 * @author Kishore Routhu on 20/3/18 12:07 PM.
 */
public abstract class EncodeUtils {

    public static String encodeContent(String content) {
        try {
            return URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new WMRuntimeException(e);
        }
    }
}
