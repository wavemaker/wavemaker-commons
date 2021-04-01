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
package com.wavemaker.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.properties.PropertiesWriter;
import com.wavemaker.commons.properties.SortedProperties;

/**
 * @author Uday Shankar
 */
public class PropertiesFileUtils {

    private static final String FILE_NOT_FOUND = "com.wavemaker.commons.file.not.found";

    private PropertiesFileUtils() {
    }

    public static Properties loadFromXml(File file) {
        try {
            return loadFromXml(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            throw new WMRuntimeException(MessageResource.create(FILE_NOT_FOUND), e, file.getAbsolutePath());
        }
    }

    public static Properties loadFromXml(InputStream is) {
        try {
            Properties properties = new Properties();
            properties.loadFromXML(is);
            return properties;
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.properties.inputStream.read.failure"), e);
        } finally {
            WMIOUtils.closeSilently(is);
        }
    }

    public static Properties loadFromXml(URL url) {
        InputStream inputStream = null;
        try {
            Properties properties = new Properties();
            inputStream = url.openStream();
            properties.loadFromXML(inputStream);
            return properties;
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.properties.inputStream.read.failure"), e);
        } finally {
            WMIOUtils.closeSilently(inputStream);
        }
    }

    public static Properties loadProperties(File file) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            return loadProperties(is);
        } catch (FileNotFoundException e) {
            throw new WMRuntimeException(MessageResource.create(FILE_NOT_FOUND), e, file.getAbsolutePath());
        }
    }

    public static Properties loadProperties(InputStream stream) {
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.load.properties"), e);
        } finally {
            WMIOUtils.closeByLogging(stream);
        }
        return properties;
    }

    public static void storeToXml(Properties properties, OutputStream os, String comment) {
        try {
            PropertiesWriter propertiesWriter = new PropertiesWriter(properties);
            propertiesWriter.setComments(comment).setToXML(true);
            propertiesWriter.write(os);
        } finally {
            WMIOUtils.closeSilently(os);
        }
    }


    public static void storeToXml(Properties properties, File file, String comment) {
        try {
            storeToXml(properties, new BufferedOutputStream(new FileOutputStream(file)), comment);
        } catch (FileNotFoundException e) {
            throw new WMRuntimeException(MessageResource.create(FILE_NOT_FOUND), e, file.getAbsolutePath());
        }
    }

    /**
     * @deprecated (this api is deprecated,use PropertiesWriter to store properties in file)
     * @param props
     * @param file
     * @param comments
     */
    @Deprecated
    public static void storeProperties(Properties props, File file, String comments) {
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            storeProperties(props, os, comments);
        } catch (FileNotFoundException e) {
            throw new WMRuntimeException(MessageResource.create(FILE_NOT_FOUND), e, file.getAbsolutePath());
        }
    }

    /**
     * @deprecated (this api is deprecated,use PropertiesWriter to store properties in file)
     * @param props
     * @param outputStream
     * @param comments
     * @param sortProperties
     */
    @Deprecated
    public static void storeProperties(
            Properties props, OutputStream outputStream, String comments, boolean sortProperties) {
        if (sortProperties) {
            props = sortProperties(props);
        }
        storeProperties(props, outputStream, comments);
    }

    /**
     * @deprecated (this api is @deprecated,use PropertiesWriter to store properties in file)
     * @param props
     * @param outputStream
     * @param comments
     */
    @Deprecated
    public static void storeProperties(Properties props, OutputStream outputStream, String comments) {
        try {
            props.store(outputStream, comments);
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.store.properties"), e);
        } finally {
            WMIOUtils.closeByLogging(outputStream);
        }
    }

    public static Properties sortProperties(Properties properties) {
        Properties sortedProperties = new SortedProperties();
        sortedProperties.putAll(properties);
        return sortedProperties;
    }

}
