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

import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.FileSystemResource;

import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.io.File;
import com.wavemaker.commons.io.local.LocalFile;

public class YamlFileUtils {

    private static final YAMLMapper yamlMapper = new YAMLMapper()
        .configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);

    public static Properties loadPropertiesFromYamlFile(File file) {
        WMYamlProcessor wmYamlProcessor = new WMYamlProcessor();
        wmYamlProcessor.setResources(new FileSystemResource(((LocalFile) file).getLocalFile().getAbsolutePath()));
        return wmYamlProcessor.getProperties();
    }

    public static void writePropertiesToYamlFile(Properties properties, File file) {
        OutputStream outputStream = null;
        try {
            outputStream = file.getContent().asOutputStream();
            Map<String, Object> map = PropertiesToMapConverter.propertiesToMapConverter(properties);
            yamlMapper.writeValue(outputStream, map);
        } catch (Exception e) {
            throw new WMRuntimeException(e);
        } finally {
            WMIOUtils.closeSilently(outputStream);
        }
    }
}
