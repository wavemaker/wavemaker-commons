package com.wavemaker.studio.common.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.wavemaker.studio.common.pattern.URLPattern;


/**
 * Created by sunilp on 7/10/15.
 */
public class CoreFilterUtil {

    public static ArrayList<URLPattern> extractExcludedUrlsList(String excludedUrls) {
        ArrayList<URLPattern> excludedUrlsList = new ArrayList<URLPattern>();
        if (excludedUrls != null) {
            StringTokenizer tokenizer = new StringTokenizer(excludedUrls, ";");
            if(tokenizer != null) {
                while (tokenizer.hasMoreTokens()) {
                    excludedUrlsList.add(URLPattern.constructPattern(tokenizer.nextToken().trim()));
                }
            }
        }
        return  excludedUrlsList;
    }

    public static ArrayList<URLPattern> extractExcludedInternalURlList(String excludedInternalUrls) {
        ArrayList<URLPattern> excludedUrlsList = new ArrayList<URLPattern>();
        if (excludedInternalUrls != null) {
            StringTokenizer tokenizer = new StringTokenizer(excludedInternalUrls, ";");
            if(tokenizer != null) {
                while (tokenizer.hasMoreTokens()) {
                    excludedUrlsList.add(URLPattern.constructPattern(tokenizer.nextToken().trim()));
                }
            }
        }
        return  excludedUrlsList;
    }

    public static boolean isExcluded(HttpServletRequest request, ArrayList<URLPattern> excludedUrls)
    {
        if(excludedUrls != null && !excludedUrls.isEmpty()) {
            String requestUri = request.getRequestURI();
            String requestPath = requestUri.substring(request.getContextPath().length());
            for (URLPattern urlPattern : excludedUrls) {
                if (urlPattern.matches(requestPath)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isExcludedInternal(HttpServletRequest request, ArrayList<URLPattern> excludedInternalUrls)
    {
        if(excludedInternalUrls != null && !excludedInternalUrls.isEmpty()) {
            String requestUri = request.getRequestURI();
            String requestPath = requestUri.substring(request.getContextPath().length());
            for (URLPattern urlPattern : excludedInternalUrls) {
                if (urlPattern.matches(requestPath)) {
                    return true;
                }
            }
        }
        return false;
    }
}