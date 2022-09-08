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

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.util.TypeConversionUtils;

/**
 * @author Simon Toens
 * @author Jeremy Grelle
 */
public class ClassLoaderUtils {

    private ClassLoaderUtils() {
    }

    /**
     * Loads class specified by className from the ContextClassLoader.
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, getClassLoader());
    }

    /**
     * Loads class specified by className from the ContextClassLoader.
     */
    public static Class<?> loadClass(String className, boolean initialize) {
        return loadClass(className, initialize, getClassLoader());
    }

    /**
     * Loads class specified by className, using passed ClassLoader, and initializing the class.
     */
    public static Class<?> loadClass(String className, ClassLoader loader) {
        return loadClass(className, true, loader);
    }

    /**
     * Loads class specified by className, using passed ClassLoader.
     */
    public static Class<?> loadClass(String className, boolean initialize, ClassLoader loader) {
        try {
            Class<?> rtn = TypeConversionUtils.primitiveForName(className);
            if (rtn == null) {
                // On Windows, if the class has been loaded from a jar, this
                // may hold an open reference to the jar.
                rtn = Class.forName(className, initialize, loader);
            }
            return rtn;
        } catch (ClassNotFoundException ex) {
            String s = ex.getMessage();
            if (s == null || s.equals("")) {
                s = "Cannot find class " + className;
            }
            throw new WMRuntimeException(s, ex);
        }
    }

    /**
     * Returns the context ClassLoader.
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static InputStream getResourceAsStream(String path) {
        return getClassLoader().getResourceAsStream(path);
    }

    /**
     * returns the loaded class in the given class loader,returns null if class is not yet loaded
     *
     * @param cl        classLoader to check for
     * @param className to check
     */
    public static Class findLoadedClass(ClassLoader cl, String className) throws InvocationTargetException, IllegalAccessException {
        if (cl == null) {
            return null;
        }
        return (Class) ClassLoaderHelper.findLoadedClassMethod.invoke(cl, className);
    }

    private static class ClassLoaderHelper {
        private static final Method findLoadedClassMethod;

        static {
            try {
                findLoadedClassMethod = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[]{String.class});
                findLoadedClassMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.initialize.class"), e);
            }
        }
    }
}
