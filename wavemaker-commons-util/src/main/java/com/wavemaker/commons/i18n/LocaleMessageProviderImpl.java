package com.wavemaker.commons.i18n;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMError;
import com.wavemaker.commons.json.JSONUtils;

/**
 * Created by prakashb on 19/7/18.
 */
public class LocaleMessageProviderImpl implements LocaleMessageProvider {

    private static final String CLASSPATH_MESSAGE_RESOURCE_PATH = "classpath*:i18n/wm";
    private static String DEFAULT_LOCALE = "en";
    private static LinkedHashSet<String> SUPPORTED_LOCALES= new LinkedHashSet();
    private static Cache<String, Map<String, String>> messages;
    private static PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    private static final Logger logger = LoggerFactory.getLogger(LocaleMessageProviderImpl.class);

    static {
        messages = CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES).build();
        init();
    }

    private static void init() {
        try {
            Resource[] resources = resolver.getResources(CLASSPATH_MESSAGE_RESOURCE_PATH + "/*.json");
            for (Resource resource : resources) {
                String locale = FilenameUtils.removeExtension(resource.getFilename());
                SUPPORTED_LOCALES.add(locale);
            }
            if (!SUPPORTED_LOCALES.contains(DEFAULT_LOCALE)) {
                if (SUPPORTED_LOCALES.isEmpty()) {
                    throw new WMError("No locales available in the project");
                } else {
                    String previousDefaultLocale = DEFAULT_LOCALE;
                    DEFAULT_LOCALE = SUPPORTED_LOCALES.iterator().next();
                    logger.warn("Default locale file for locale {} not found, using {} as the default locale", previousDefaultLocale, DEFAULT_LOCALE);
                }
            }
        } catch (IOException e) {
            throw new WMError("Failed to read locale files", e);
        }
    }

    @Override
    public String getLocaleMessage(String locale, MessageResource messageResource) {
        Map<String, String> localeMessages = getLocaleMessages(locale);
        return localeMessages.get(messageResource.getMessageKey());
    }

    @Override
    public String getLocaleMessage(String locale, MessageResource messageResource, Object[] args) {
        String localeMessage = getLocaleMessage(locale, messageResource);
        return MessageFormat.format(localeMessage, args);
    }

    public Map<String, String> getLocaleMessages(String locale) {
        if (!SUPPORTED_LOCALES.contains(locale)) {
            locale = DEFAULT_LOCALE;
        }
        Map<String, String> existingMessages = messages.getIfPresent(locale);
        if (existingMessages == null) {
            synchronized (messages) {
                existingMessages = messages.getIfPresent(locale);
                if (existingMessages == null) {
                    existingMessages = new HashMap<>();
                    try {
                        Resource[] resources = resolver.getResources(CLASSPATH_MESSAGE_RESOURCE_PATH + "/" + locale + ".json");
                        for (Resource resource : resources) {
                            Map<String, String> localeMessages = JSONUtils.toObject(resource.getInputStream(), new TypeReference<Map<String, String>>() {});
                            existingMessages.putAll(localeMessages);
                        }
                    } catch (IOException e) {
                        throw new WMError("Failed to read locale resources for locale " + locale, e);
                    }
                    messages.put(locale, existingMessages);
                }
            }
        }
        return existingMessages;
    }
}
