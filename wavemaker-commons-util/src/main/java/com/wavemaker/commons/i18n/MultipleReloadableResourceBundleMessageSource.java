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
package com.wavemaker.commons.i18n;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Created by saddhamp on 23/3/16.
 */
public class MultipleReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
    private static final String PROPERTIES_SUFFIX = ".properties";
    private static final String XML_SUFFIX = ".xml";

    private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @Override
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        if (filename.startsWith(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
            return refreshClassPathProperties(filename, propHolder);
        } else {
            return super.refreshProperties(filename, propHolder);
        }
    }

    private PropertiesHolder refreshClassPathProperties(String filename, PropertiesHolder propHolder) {
        Properties properties = new Properties();
        long lastModified = -1;

        try {
            Resource[] resources = null;
            Resource[] propertiesResources = resolver.getResources(filename + PROPERTIES_SUFFIX);
            Resource[] xmlResources = resolver.getResources(filename + XML_SUFFIX);

            resources = getMergedResources(propertiesResources, xmlResources);

            if (resources.length > 0) {
                String sourcePath = null;
                PropertiesHolder holder = null;
                for (Resource resource : resources) {
                    sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
                    holder = super.refreshProperties(sourcePath, propHolder);
                    properties.putAll(holder.getProperties());
                    if (lastModified < resource.lastModified()) {
                        lastModified = resource.lastModified();
                    }
                }
            }
        } catch (IOException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(filename + " could not be resolved in the file system", ex);
            }
        }

        return new PropertiesHolder(properties, lastModified);
    }

    private Resource[] getMergedResources(Resource[] resources1, Resource[] resources2) {
        if (resources1 == null && resources2 == null){
            return new Resource[0];
        } if (resources1 == null){
            return resources2;
        } else if (resources2 == null){
            return  resources1;
        }

        Resource [] resources = new Resource[resources1.length+resources2.length];

        int index = 0;
        int i;
        for (i=0; i<resources1.length; i++, index++){
            resources[index] = resources1[i];
        }

        for (i=0; i<resources2.length; i++, index++){
            resources[index] = resources2[i];
        }

        return resources;
    }
}