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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.FileSystemResource;

import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.io.File;
import com.wavemaker.commons.io.local.LocalFile;
import com.wavemaker.commons.properties.PropertiesWriter;
import com.wavemaker.commons.properties.SortedProperties;

/**
 * @author Uday Shankar
 */
public class PropertiesFileUtils {

    private static final YAMLMapper yamlMapper = new YAMLMapper()
        .configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false)
        .configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true);

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

    public static Properties loadYamlProperties(File file) {
        //TODO YamlPropertiesFactoryBean converting yaml list objects to [0],[1] etc
/*        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new FileSystemResource(((LocalFile) file).getLocalFile().getAbsolutePath()));
        return yamlPropertiesFactoryBean.getObject();*/

        //TODO Using snake yaml directly to convert yaml to properties
/*        Yaml yaml = new Yaml();
        Map<String, Object> yamlMap = yaml.load(file.getContent().asInputStream());
        Properties properties = new Properties();
        if (yamlMap != null) {
            flattenMap("", yamlMap, properties);
        }
        return properties;*/
        WMYamlPropertiesFactoryBean yamlPropertiesFactoryBean = new WMYamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new FileSystemResource(((LocalFile) file).getLocalFile().getAbsolutePath()));
        return yamlPropertiesFactoryBean.getObject();
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

    public static void storeToYAML(Properties properties, File file) {
        OutputStream outputStream = null;
        try {
            outputStream = file.getContent().asOutputStream();
            Map<String, Object> map = new LinkedHashMap<>();
            properties.forEach((key, value) -> {
                String[] propertyKeyArray = key.toString().split("\\.");
                Map<String, Object> nestedMap = map;
                for (int index = 0; index < propertyKeyArray.length - 1; index++) {
                    nestedMap = (Map<String, Object>) nestedMap.computeIfAbsent(propertyKeyArray[index], k -> new LinkedHashMap<>());
                }
                nestedMap.put(propertyKeyArray[propertyKeyArray.length - 1], properties.get(key.toString()));
            });
            yamlMapper.writeValue(outputStream, map);
        } catch (Exception e) {
            throw new WMRuntimeException(e);
        } finally {
            WMIOUtils.closeSilently(outputStream);
        }
    }
}
