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
package com.wavemaker.commons.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.util.IOUtils;

/**
 * Created by venuj on 19-05-2014.
 */
public class JSONUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setTypeFactory(TypeFactory.defaultInstance().withClassLoader(JSONUtils.class.getClassLoader()));
    }

    public static String prettifyJSON(String data) throws IOException {
        if (StringUtils.isNotBlank(data)) {
            JsonNode tree = objectMapper.readTree(data);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree);
        }
        return data;
    }

    public static void prettifyJSON(String data, File outputFile) throws IOException {
        if (data == null) {
            throw new WMRuntimeException("Can not prettify and persist null data");
        }
        String formattedJson = prettifyJSON(data);
        IOUtils.write(outputFile, formattedJson);
    }

    public static String toJSON(Object object) throws IOException {
        return toJSON(object, true);
    }

    public static String toJSON(Object object, boolean prettify) throws IOException {
        return prettify ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object) : objectMapper
                .writeValueAsString(object);
    }

    public static void toJSON(File outputFile, Object object) throws IOException {
        toJSON(outputFile, object, true);
    }

    public static void toJSON(File outputFile, Object object, boolean prettify) throws IOException {
        if (prettify)
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, object);
        else
            objectMapper.writeValue(outputFile, object);
    }

    public static void toJSON(OutputStream outputStream, Object object) throws IOException {
        toJSON(outputStream, object, true);
    }

    public static void toJSON(OutputStream outputStream, Object object, boolean prettify) throws IOException {
        if (prettify)
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, object);
        else
            objectMapper.writeValue(outputStream, object);
    }

    public static <T> T toObject(String jsonString, Class<T> t) throws IOException {
        return (T) objectMapper.readValue(jsonString, t);
    }

    public static <T> T toObject(InputStream jsonStream, Class<T> t) throws IOException {
        return (T) objectMapper.readValue(jsonStream, t);
    }

    public static <T> T toObject(File file, Class<T> targetClass) throws IOException {
        return (T) objectMapper.readValue(file, targetClass);
    }

    public static <T> T toObject(File file, JavaType javaType) throws IOException {
        return (T) objectMapper.readValue(file, javaType);
    }

    public static <T> T toObject(String jsonString, TypeReference<T> valueTypeRef) throws IOException {
        return objectMapper.readValue(jsonString, valueTypeRef);
    }

    public static <T> T toObject(InputStream inputStream, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(inputStream, typeReference);
    }

    public static void registerModule(Module module) {
        objectMapper.registerModule(module);
    }

}
