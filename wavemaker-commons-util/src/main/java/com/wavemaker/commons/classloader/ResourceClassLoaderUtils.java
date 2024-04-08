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
package com.wavemaker.commons.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.io.Resource;
import com.wavemaker.commons.io.ResourceURL;

/**
 * Class Loader Utils specifically designed to work with {@link Resource}s. Migrated from
 * {@link ClassLoaderUtils} in the common project for dependency reasons.
 */
public class ResourceClassLoaderUtils {

    private static final Logger logger = LoggerFactory.getLogger(ResourceClassLoaderUtils.class);

    private ResourceClassLoaderUtils() {
    }

    public static ClassLoader getClassLoaderForResources(final ClassLoader parent, Resource... resources) {
        return getClassLoaderForResources(false, parent, resources);
    }

    public static ClassLoader getClassLoaderForResources(boolean nonLocking, final ClassLoader parent, Resource... resources) {
        try {
            final URL[] urls = ResourceURL.getForResources(Arrays.asList(resources), nonLocking).toArray(new URL[resources.length]);
            return new URLClassLoader(urls, parent);
        } catch (MalformedURLException ex) {
            throw new AssertionError(ex);
        }
    }

    public static void runInClassLoaderContext(Runnable runnable, ClassLoader cl) {
        runInClassLoaderContext(asCallable(runnable), null, cl);
    }

    public static <V> V runInClassLoaderContext(WMCallable<V> executable, ClassLoader cl) {
        return runInClassLoaderContext(executable, null, cl);
    }

    public static <V> V runInClassLoaderContext(WMCallable<V> executable, Runnable finalizerTask, ClassLoader cl) {
        ClassLoader c = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(cl);
            return executable.call();
        } finally {
            try {
                if (finalizerTask != null) {
                    finalizerTask.run();
                }
            } catch (Exception e) {
                logger.warn("Failed to execute finalizer task", e);
            } finally {
                Thread.currentThread().setContextClassLoader(c);
            }
        }
    }

    private static WMCallable<Object> asCallable(final Runnable runnable) {
        return () -> {
            runnable.run();
            return null;
        };
    }
}
