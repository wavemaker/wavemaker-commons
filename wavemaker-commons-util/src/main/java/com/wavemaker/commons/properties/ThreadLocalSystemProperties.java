package com.wavemaker.commons.properties;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Support for storing System.setProperty calls in thread local.
 * Mainly used to deal with third party libraries which sets new system properties and never clear them.
 * <p>
 * To Enable usage of this class a single call to {@link #enable()} has to be done.
 * To Disable usage of this class, a call to {@link #disable()} need to be made. This needs to be done to remove all
 * references for this class's classloader for
 * smooth class unloading.
 * <p>
 * Calling {@link #record()} before any such operation will store all calls to {@link System#setProperty(String,
 * String)} in the current thread local only.
 * Even for properties which are already defined in global level a new property is created in thread local
 * Also calls to {@link System#getProperty(String)} will return values from current thread local and falls back to
 * global vm level properties if not present
 * in thread local.
 * <p>
 * Calling {@link #stop()} will stop storing in thread local and any call to {@link System#setProperty(String, String)}
 * will be store in global vm level
 * properties for the current thread.
 *
 * @author Uday Shankar
 */

public class ThreadLocalSystemProperties extends Properties {

    private static final ThreadLocal<Properties> THREAD_LOCAL_PROPERTIES = new ThreadLocal<>();

    private static final Properties VM_LEVEL_PROPERTIES = System.getProperties();

    private static final ThreadLocalSystemProperties INSTANCE = new ThreadLocalSystemProperties();

    private static final Logger logger = LoggerFactory.getLogger(ThreadLocalSystemProperties.class);

    private ThreadLocalSystemProperties() {
        super(VM_LEVEL_PROPERTIES);
    }

    /**
     * Will set ThreadLocalProperties as the default System properties at the vm level
     * Existing System properties are still available by default at global level across all threads
     */
    public synchronized ThreadLocalSystemProperties enable() {
        Properties currentSystemProperties = System.getProperties();
        if (currentSystemProperties instanceof ThreadLocalSystemProperties) {
            logger.info("ThreadLocal based Properties is already set for System Properties");
        } else {
            logger.info("Setting ThreadLocal based Properties for System.setProperties");
            System.setProperties(ThreadLocalSystemProperties.INSTANCE);
            logger.info("Set ThreadLocal based Properties for System.setProperties!!");
        }
        return INSTANCE;
    }

    /**
     * Will only disable ThreadLocalProperties at the vm level
     * Vm level properties previously set will still be present
     */
    public synchronized void disable() {
        Properties currentSystemProperties = System.getProperties();
        if (currentSystemProperties instanceof ThreadLocalSystemProperties) {
            logger.info("Removing ThreadLocal based Properties for System.setProperties");
            System.setProperties(VM_LEVEL_PROPERTIES);
            logger.info("Removed ThreadLocal based Properties for System.setProperties");
        } else {
            logger.warn("ThreadLocal based Properties is not enabled for System.setProperties");
        }
    }

    public static synchronized void executeInThreadLocalSystemProperties(Runnable runnable) {
        try {
            INSTANCE.record();
            runnable.run();
        } finally {
            INSTANCE.stop();
        }
    }

    /**
     * Will store properties in thread local on calling System.setProperty post this method call in the current thread
     */
    private synchronized void record() {
        Properties currentSystemProperties = System.getProperties();
        if (currentSystemProperties instanceof ThreadLocalSystemProperties) {
            Properties threadLocalProperties = getLocalProperties();
            if (threadLocalProperties instanceof ThreadLocalSystemProperties) {
                logger.info("Usage of Thread Local properties for current thread is already enabled");
            } else {
                logger.info("Enabling usage of Thread Local properties for current thread");
                THREAD_LOCAL_PROPERTIES.set(new Properties(VM_LEVEL_PROPERTIES));
                logger.info("Enabled usage of Thread Local properties for current thread");
            }
        } else {
            throw new IllegalStateException(
                    "Request for recording ThreadLocal for current thread without enabling the ThreadLocalSystemProperties");
        }
    }

    /**
     * Will use global vm level properties to store properties in the current thread
     */
    private synchronized void stop() {
        Properties currentSystemProperties = System.getProperties();
        if (currentSystemProperties instanceof ThreadLocalSystemProperties) {
            logger.info("Disabling usage of Thread Local properties for current thread");
            THREAD_LOCAL_PROPERTIES.remove();
            logger.info("Disabled usage of Thread Local properties for current thread");
        } else {
            throw new IllegalStateException(
                    "Request for stopping ThreadLocal for current thread without enabling the ThreadLocalSystemProperties");
        }
    }

    @Override
    public String getProperty(String key) {
        return getLocalProperties().getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return getLocalProperties().getProperty(key, defaultValue);
    }

    @Override
    public Object setProperty(String key, String value) {
        return getLocalProperties().setProperty(key, value);
    }

    @Override
    public Enumeration<?> propertyNames() {
        return getLocalProperties().propertyNames();
    }

    @Override
    public Set<String> stringPropertyNames() {
        return getLocalProperties().stringPropertyNames();
    }

    @Override
    public void list(PrintStream out) {
        getLocalProperties().list(out);
    }

    @Override
    public void list(PrintWriter out) {
        getLocalProperties().list(out);
    }

    @Override
    public Set<Object> keySet() {
        Properties localProperties = getLocalProperties();
        if (localProperties == VM_LEVEL_PROPERTIES) {
            return localProperties.keySet();
        } else {
            return entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toSet());
        }
    }

    @Override
    public Set<Object> values() {
        Properties localProperties = getLocalProperties();
        if (localProperties == VM_LEVEL_PROPERTIES) {
            return localProperties.keySet();
        } else {
            return entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        }
    }

    @Override
    public int size() {
        Properties localProperties = getLocalProperties();
        if (localProperties == VM_LEVEL_PROPERTIES) {
            return localProperties.size();
        } else {
            return entrySet().size();
        }
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        Properties localProperties = getLocalProperties();
        if (localProperties == VM_LEVEL_PROPERTIES) {
            return localProperties.entrySet();
        } else {
            Set<Map.Entry<Object, Object>> parentEntries = VM_LEVEL_PROPERTIES.entrySet();
            Set<Map.Entry<Object, Object>> childEntries = localProperties.entrySet();
            Map<Object, Map.Entry<Object, Object>> entrySetMap = new HashMap<>(parentEntries.size());
            parentEntries.forEach(entry -> entrySetMap.put(entry.getKey(), entry));
            childEntries.forEach(entry -> entrySetMap.put(entry.getKey(), entry));
            return Collections.unmodifiableSet(new HashSet<>(entrySetMap.values()));
        }
    }

    private static Properties getLocalProperties() {
        Properties threadLocalProperties = THREAD_LOCAL_PROPERTIES.get();
        if (threadLocalProperties == null) {
            threadLocalProperties = VM_LEVEL_PROPERTIES;
        }
        return threadLocalProperties;
    }
}