/**
 * Copyright © 2015 WaveMaker, Inc.
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
package com.wavemaker.studio.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import com.wavemaker.studio.common.WMRuntimeException;

/**
 * @author Uday Shankar
 */
public class PropertiesFileUtils {

    public static Properties loadFromXml(File file) {
        try {
            return loadFromXml(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            throw new WMRuntimeException("File:" + file.getAbsolutePath() + " not found", e);
        }
    }

    public static Properties loadFromXml(InputStream is) {
        try {
            Properties properties = new Properties();
            properties.loadFromXML(is);
            return properties;
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to read properties input stream", e);
        } finally {
            IOUtils.closeSilently(is);
        }
    }

    public static void storeToXml(Properties properties, File file, String comment) {
        try {
            storeToXml(properties, new BufferedOutputStream(new FileOutputStream(file)), comment);
        } catch (FileNotFoundException e) {
            throw new WMRuntimeException("File:" + file.getAbsolutePath() + " not found", e);
        }
    }

    public static void storeToXml(Properties properties, OutputStream os, String comment) {
        try {
            properties.storeToXML(os, comment, "UTF-8");
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to write properties file to output stream", e);
        } finally {
            IOUtils.closeSilently(os);
        }
    }

    public static Properties loadProperties(File file) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            return loadProperties(is);
        } catch (FileNotFoundException e) {
            throw new WMRuntimeException("File:" + file.getAbsolutePath() + " not found", e);
        }
    }

    public static Properties loadProperties(InputStream stream) {
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to load properties.", e);
        } finally {
            IOUtils.closeByLogging(stream);
        }
        return properties;
    }

    public static void storeProperties(Properties props, File file, String comments) {
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            storeProperties(props, os, comments);
        } catch (FileNotFoundException e) {
            throw new WMRuntimeException("File:" + file.getAbsolutePath() + " not found", e);
        }
    }

    public static void storeProperties(Properties props, OutputStream outputStream, String comments, boolean sortProperties) {
        if (sortProperties) {
            props = sortProperties(props);
        }
        storeProperties(props, outputStream, comments);
    }

    public static void storeProperties(Properties props, OutputStream outputStream, String comments) {
        try {
            props.store(outputStream, comments);
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to store properties.", e);
        } finally {
            IOUtils.closeByLogging(outputStream);
        }
    }

    public static Properties sortProperties(Properties properties) {
        Properties sortedProperties = new SortedProperties();
        sortedProperties.putAll(properties);
        return sortedProperties;
    }

    public static class SortedProperties extends Properties {
        @Override
        public Enumeration keys() {
            Enumeration keysEnum = super.keys();
            List<String> keyList = new ArrayList<String>();
            while(keysEnum.hasMoreElements()){
                keyList.add((String)keysEnum.nextElement());
            }
            Collections.sort(keyList);
            return Collections.enumeration(keyList);
        }

        @Override
        public Set<String> stringPropertyNames() {
            return new TreeSet<>(super.stringPropertyNames());
        }
    }
}
