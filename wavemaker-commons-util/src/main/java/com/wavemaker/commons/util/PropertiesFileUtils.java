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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.io.File;
import com.wavemaker.commons.properties.PropertiesWriter;
import com.wavemaker.commons.properties.SortedProperties;

/**
 * @author Uday Shankar
 */
public class PropertiesFileUtils {

    private PropertiesFileUtils() {
    }

    public static Properties loadFromXml(File file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getContent().asInputStream();
            Properties properties = new Properties();
            properties.loadFromXML(inputStream);
            return properties;
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.properties.inputStream.read.failure"), e);
        } finally {
            WMIOUtils.closeSilently(inputStream);
        }
    }

    public static Properties loadProperties(File file) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = file.getContent().asInputStream();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.load.properties"), e);
        } finally {
            WMIOUtils.closeByLogging(inputStream);
        }
        return properties;
    }

    public static void storeToXml(Properties properties, File file, String comment) {
        OutputStream outputStream = null;
        try {
            outputStream = file.getContent().asOutputStream();
            PropertiesWriter propertiesWriter = new PropertiesWriter(properties);
            propertiesWriter.setComments(comment).setToXML(true).setSortProperties(true);
            propertiesWriter.write(outputStream);
        } finally {
            WMIOUtils.closeSilently(outputStream);
        }
    }

    public static Properties sortProperties(Properties properties) {
        Properties sortedProperties = new SortedProperties();
        sortedProperties.putAll(properties);
        return sortedProperties;
    }
}
