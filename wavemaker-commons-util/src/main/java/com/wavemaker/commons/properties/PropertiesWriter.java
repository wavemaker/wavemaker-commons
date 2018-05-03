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
package com.wavemaker.commons.properties;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.util.WMIOUtils;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 14/4/16
 */
public class PropertiesWriter {

    private Properties properties;
    private String comments;
    private boolean sortProperties;
    private boolean sansDate;
    private boolean toXML;

    public PropertiesWriter(Properties properties) {
        this.properties = properties;
        init();
    }

    public static PropertiesWriter newWriter(Properties properties) {
        return new PropertiesWriter(properties);
    }

    private void init() {
        if (properties == null) {
            throw new WMRuntimeException("Properties can not be null");
        }
    }

    public PropertiesWriter setComments(final String comments) {
        this.comments = comments;
        return this;
    }

    public PropertiesWriter setSortProperties(final boolean sortProperties) {
        this.sortProperties = sortProperties;
        return this;
    }

    public PropertiesWriter setSansDate(final boolean sansDate) {
        this.sansDate = sansDate;
        return this;
    }

    public PropertiesWriter setToXML(final boolean toXML) {
        this.toXML = toXML;
        return this;
    }

    public synchronized void write(File file) throws FileNotFoundException {
        if (file == null) {
            throw new WMRuntimeException("File can not be null");
        }
        write(new BufferedOutputStream(new FileOutputStream(file)));
    }

    public synchronized void write(com.wavemaker.commons.io.File file) {
        write(file.getContent().asOutputStream());
    }

    public synchronized void write(OutputStream stream) {
        if (stream == null) {
            throw new WMRuntimeException("Output stream can not be null");
        }

        sort();

        if (toXML) {
            storeToXml(stream);
        } else if (sansDate) {
            storeSansDate(stream);
        } else {
            storeProperties(stream);
        }
    }


    protected Properties sort() {
        if (sortProperties) {
            Properties sortedProperties = new SortedProperties();
            sortedProperties.putAll(properties);
            properties = sortedProperties;
        }
        return properties;
    }

    protected void storeProperties(OutputStream outputStream) {
        try {
            properties.store(outputStream, comments);
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to store properties.", e);
        } finally {
            WMIOUtils.closeSilently(outputStream);
        }
    }

    protected void storeToXml(OutputStream os) {
        try {
            properties.storeToXML(os, comments, "UTF-8");
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to write properties file to output stream", e);
        } finally {
            WMIOUtils.closeSilently(os);
        }
    }

    /**
     * This api use apache commons property configuration to persist properties into file
     * and this api will avoid writing current date as comment into property file.
     *
     * @param os
     */
    protected void storeSansDate(OutputStream os) {
        PropertiesConfiguration configuration = new PropertiesConfiguration();
        configuration.getLayout().setGlobalSeparator("=");
        Enumeration enumeration = properties.keys();
        boolean canComment = true;
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            if (canComment && StringUtils.isNotBlank(comments)) {
                configuration.getLayout().setComment(key, comments);
                canComment = false;
            }
            configuration.setProperty(key, properties.get(key));
        }
        try {
            configuration.getLayout().save(configuration, new OutputStreamWriter(os));

        } catch (ConfigurationException e) {
            throw new WMRuntimeException("Unable to write properties to output stream", e);
        } finally {
            WMIOUtils.closeSilently(os);
        }
        configuration.clear();
    }

}
