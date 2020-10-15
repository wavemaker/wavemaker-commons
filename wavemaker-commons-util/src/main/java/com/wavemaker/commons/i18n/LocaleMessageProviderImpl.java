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
package com.wavemaker.commons.i18n;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.json.JSONUtils;

/**
 * Created by prakashb on 19/7/18.
 */
public class LocaleMessageProviderImpl implements LocaleMessageProvider {

    private static final String CLASSPATH_MESSAGE_RESOURCE_PATH = "classpath*:i18n/wm";

    private LinkedHashSet<String> supportedLocales = new LinkedHashSet();
    private String defaultLocale = "en";

    private Cache<String, Map<String, String>> messages = CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES).build();
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private List<String> locationPatterns;

    private static final Logger logger = LoggerFactory.getLogger(LocaleMessageProviderImpl.class);

    public LocaleMessageProviderImpl() {
        this(Arrays.asList(CLASSPATH_MESSAGE_RESOURCE_PATH), new PathMatchingResourcePatternResolver());
    }

    public LocaleMessageProviderImpl(List<String> locationPatterns, ResourcePatternResolver resourcePatternResolver) {
        this.locationPatterns = locationPatterns;
        this.resourcePatternResolver = resourcePatternResolver;
        init();
    }

    private synchronized void init() {
        List<Resource> resourceList = getResourceList("*.json");
        for (Resource resource : resourceList) {
            String locale = FilenameUtils.removeExtension(resource.getFilename());
            supportedLocales.add(locale);
        }
        if (!supportedLocales.contains(defaultLocale)) {
            if (supportedLocales.isEmpty()) {
                throw new WMRuntimeException("No locales available in the project");
            } else {
                String previousDefaultLocale = defaultLocale;
                defaultLocale = supportedLocales.iterator().next();
                logger.warn("Default locale file for locale {} not found, using {} as the default locale", previousDefaultLocale, defaultLocale);
            }
        }
    }

    @Override
    public String getLocaleMessage(String[] locales, MessageResource messageResource, String defaultMessage) {
        String localMessage = getLocaleMessage(locales, messageResource);
        if(localMessage != null){
            return localMessage;
        } else {
            logger.warn("message for {} not found and default message is {}", messageResource.getMessageKey(), defaultMessage);
            return defaultMessage;
        }
    }

    @Override
    public String getLocaleMessage(String[] locales, MessageResource messageResource, String defaultMessage, Object[] args) {
        String localeMessage = getLocaleMessage(locales, messageResource);
        if (localeMessage != null) {
            return MessageFormat.format(localeMessage, args);
        } else {
            logger.warn("message for {} not found, its args are {} and default message is {}", messageResource.getMessageKey(), args, defaultMessage);
            return defaultMessage;
        }
    }

    private String getLocaleMessage(String[] locales, MessageResource messageResource) {
        Map<String, String> localeMessages = getLocaleMessages(locales);
        return localeMessages.get(messageResource.getMessageKey());
    }

    private Map<String, String> getLocaleMessages(String... locales) {
        for (String locale : locales) {
            if (!supportedLocales.contains(locale)) {
                continue;
            }
            Map<String, String> existingMessages = messages.getIfPresent(locale);
            if (existingMessages == null) {
                synchronized (messages) {
                    existingMessages = messages.getIfPresent(locale);
                    if (existingMessages == null) {
                        existingMessages = loadLocaleMessages(locale);
                        messages.put(locale, existingMessages);
                    }
                }
            }
            return existingMessages;
        }
        return getLocaleMessages(defaultLocale);
    }

    protected Map<String, String> loadLocaleMessages(String locale) {
        Map<String, String> existingMessages = new HashMap<>();
        try {
            List<Resource> resourceList = getResourceList(locale + ".json");
            for (Resource resource : resourceList) {
                                Map<String, String> localeMessages = JSONUtils.toObject(resource.getInputStream(), new TypeReference<Map<String, String>>() {});
                existingMessages.putAll(localeMessages);
            }
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to read locale resources for locale " + locale, e);
        }
        return existingMessages;
    }

    protected Resource[] getResources(String locationPattern, String resourcePattern) {
        try {
            return resourcePatternResolver.getResources(locationPattern + "/" + resourcePattern);
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to read locale files", e);
        }
    }

    private List<Resource> getResourceList(String resourcePattern) {
        List<Resource> resourceList = new ArrayList<>();
        for (String locationPattern : locationPatterns) {
            resourceList.addAll(Arrays.asList(getResources(locationPattern, resourcePattern)));
        }
        return resourceList;
    }
}
