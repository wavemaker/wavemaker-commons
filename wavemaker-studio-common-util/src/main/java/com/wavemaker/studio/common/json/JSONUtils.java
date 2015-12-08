package com.wavemaker.studio.common.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by venuj on 19-05-2014.
 */
public class JSONUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    public static void toJSON(File outputFile, Object object) throws IOException {
        objectMapper.writeValue(outputFile, object);
    }

    public static void toJSON(OutputStream outputStream, Object object) throws IOException {
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

    public static void registerModule(Module module) {
        objectMapper.registerModule(module);
    }

}
