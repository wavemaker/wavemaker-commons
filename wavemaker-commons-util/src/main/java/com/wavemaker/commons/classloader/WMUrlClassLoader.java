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

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class WMUrlClassLoader extends URLClassLoader {
    private String loaderContext;
    
    static {
        ClassLoader.registerAsParallelCapable();
    }


    public WMUrlClassLoader(URL[] urls, String loaderContext, ClassLoader parent) {
        super(urls, parent);
        this.loaderContext = loaderContext;
    }

    public WMUrlClassLoader(URL[] urls, String loaderContext) {
        this(urls, loaderContext, Thread.currentThread().getContextClassLoader());
    }

    @Override
    public String toString() {
        return "WMUrlClassLoader{loaderContext='" + loaderContext + '\'' +"} ";
    }

    /**
     * If we want to load a resource and it is available in both the parent classloader and the child classloader,
     * then the default behaviour is, first it will check in the parent classloader if it not found then it will check in the child classloader.
     * So if we want to load the this type resource, we will always get the resource from the parent classloader, child's resource will never get loaded.
     *
     * To solve this first we are checking in the child classloader, if it is not found then checking in the parent classloader.
     * @param name The resource name
     * @return  A <tt>URL</tt> object for reading the resource, or
     *          <tt>null</tt> if the resource could not be found or the invoker
     *          doesn't have adequate  privileges to get the resource.
     */
    @Override
    public URL getResource(String name) {
        URL url = findResource(name);
        if (url == null) {
            url = super.getResource(name);
        }
        return url;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        Enumeration<URL>[] tmp = (Enumeration<URL>[]) new Enumeration<?>[2];
        tmp[0] = findResources(name);
        tmp[1] = getParent() != null ? getParent().getResources(name) : Collections.emptyEnumeration();
        return new CompoundEnumeration(tmp);
    }

    public static class CompoundEnumeration<E> implements Enumeration<E> {
        private Enumeration<E>[] enums;
        private int index;

        public CompoundEnumeration(Enumeration<E>[] enums) {
            this.enums = enums;
        }

        private boolean next() {
            while (index < enums.length) {
                if (enums[index] != null && enums[index].hasMoreElements()) {
                    return true;
                }
                index++;
            }
            return false;
        }

        @Override
        public boolean hasMoreElements() {
            return next();
        }

        @Override
        public E nextElement() {
            if (!next()) {
                throw new NoSuchElementException();
            }
            return enums[index].nextElement();
        }
    }
}
