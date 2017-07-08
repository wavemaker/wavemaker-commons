/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.wavemaker.commons.pattern.URLPattern;


/**
 * Created by sunilp on 7/10/15.
 */
public class CoreFilterUtil {

    public static ArrayList<URLPattern> extractExcludedUrlsList(String excludedUrls) {
        ArrayList<URLPattern> excludedUrlsList = new ArrayList<>();
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

    public static boolean isExcluded(HttpServletRequest request, List<URLPattern> excludedUrls)
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

}