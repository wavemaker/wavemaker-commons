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
package com.wavemaker.commons.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.SqlDateSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.util.WMIOUtils;

/**
 * Created by venuj on 19-05-2014.
 */
public class JSONUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setTypeFactory(TypeFactory.defaultInstance().withClassLoader(JSONUtils.class.getClassLoader()));
        final SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class,
                new SqlDateSerializer().withFormat(false, new SimpleDateFormat("yyyy-MM-dd"))
        );
        objectMapper.registerModule(module);
    }

    private JSONUtils() {
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
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.null.json.prettify.error"));
        }
        String formattedJson = prettifyJSON(data);
        WMIOUtils.write(outputFile, formattedJson);
    }

    public static String toJSON(Object object) {
        try {
            return toJSON(object, true);
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.objToJsonConversionFailed"), e);
        }
    }

    public static String toJSON(Object object, boolean prettify) throws IOException {
        return prettify ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object) : objectMapper
                .writeValueAsString(object);
    }

    public static void toJSON(File outputFile, Object object) throws IOException {
        toJSON(outputFile, object, true);
    }

    public static void toJSON(File outputFile, Object object, boolean prettify) throws IOException {
        if (prettify) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, object);
        } else {
            objectMapper.writeValue(outputFile, object);
        }
    }

    public static void toJSON(OutputStream outputStream, Object object) throws IOException {
        toJSON(outputStream, object, true);
    }

    public static void toJSON(OutputStream outputStream, Object object, boolean prettify) throws IOException {
        try {
            if (prettify) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, object);
            } else {
                objectMapper.writeValue(outputStream, object);
            }
        } finally {
            WMIOUtils.closeSilently(outputStream);
        }
    }

    public static void toJSON(Writer outputWriter, Object object) throws IOException {
        toJSON(outputWriter, object, true);
    }

    public static void toJSON(Writer outputWriter, Object object, boolean prettify) throws IOException {
        try {
            if (prettify) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputWriter, object);
            } else {
                objectMapper.writeValue(outputWriter, object);
            }
        } finally {
            WMIOUtils.closeSilently(outputWriter);
        }
    }

    public static <T> T toObject(String jsonString, Class<T> t) throws IOException {
        return objectMapper.readValue(jsonString, t);
    }

    public static <T> T toObject(Reader jsonReader, Class<T> t) throws IOException {
        try {
            return objectMapper.readValue(jsonReader, t);
        } finally {
            WMIOUtils.closeSilently(jsonReader);
        }
    }


    public static <T> T toObject(InputStream jsonStream, Class<T> t) throws IOException {
        try {
            return objectMapper.readValue(jsonStream, t);
        } finally {
            WMIOUtils.closeSilently(jsonStream);
        }
    }

    public static <T> T toObject(File file, Class<T> targetClass) throws IOException {
        return objectMapper.readValue(file, targetClass);
    }

    public static <T> T toObject(File file, JavaType javaType) throws IOException {
        return objectMapper.readValue(file, javaType);
    }

    public static <T> T toObject(String jsonString, TypeReference<T> valueTypeRef) throws IOException {
        return objectMapper.readValue(jsonString, valueTypeRef);
    }

    public static <T> T toObject(InputStream inputStream, TypeReference<T> typeReference) throws IOException {
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } finally {
            WMIOUtils.closeSilently(inputStream);
        }
    }

    public static <T> T toObject(Reader reader, TypeReference<T> typeReference) throws IOException {
        try {
            return objectMapper.readValue(reader, typeReference);
        } finally {
            WMIOUtils.closeSilently(reader);
        }
    }

    public static <T> T convert(Object object, Class<T> targetClass) {
        return objectMapper.convertValue(object, targetClass);
    }

    public static void registerModule(Module module) {
        objectMapper.registerModule(module);
    }

    public static JsonNode readTree(InputStream inputStream) {
        try {
            return objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.jsonData.parse.failed"), e);
        } finally {
            WMIOUtils.closeSilently(inputStream);
        }
    }

    public static JsonNode readTree(String str) {
        try {
            return objectMapper.readTree(str);
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.jsonData.parse.failed"), e);
        }
    }

}
