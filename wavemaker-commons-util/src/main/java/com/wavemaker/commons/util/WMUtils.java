/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.wrapper.BooleanWrapper;
import com.wavemaker.commons.wrapper.IntegerWrapper;
import com.wavemaker.commons.wrapper.StringWrapper;

/**
 * @author Uday Shankar
 */
public class WMUtils {

    public static final String SUCCESS = "success";

    public static final StringWrapper SUCCESS_RESPONSE = new StringWrapper(SUCCESS);

    private WMUtils() {
    }

    public static String getFileExtensionFromFileName(String fileName) {
        int indexOfDot = fileName.lastIndexOf('.');
        return (indexOfDot == -1) ? "":fileName.substring(indexOfDot + 1);
    }

    public static String decodeRequestURI(String requestURI) {
        try {
            return URLDecoder.decode(requestURI, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.decode.request.uri"), e);
        }
    }

    public static String[] getStringList(Object obj) {
        if (obj instanceof String) {
            return new String[]{(String) obj};
        }
        if (obj instanceof String[]) {
            return  (String[]) obj;
        }
        if (obj instanceof List) {
            List o = (List) obj;
            return (String[]) o.toArray(new String[]{});
        }
        throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.unsupported.object.type"), obj.getClass());
    }

    public static StringWrapper wrapString(String response) {
        return new StringWrapper(response);
    }

    public static IntegerWrapper wrapInteger(Integer response) {
        return new IntegerWrapper(response);
    }

    public static BooleanWrapper wrapBoolean(Boolean response) {
        return new BooleanWrapper(response);
    }
}
