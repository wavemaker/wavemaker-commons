/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
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
package com.wavemaker.studio.common.classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class WMUrlClassLoader extends URLClassLoader {
    private String loaderContext;


    public WMUrlClassLoader(URL[] urls, String loaderContext) {
        super(urls, Thread.currentThread().getContextClassLoader());
        this.loaderContext = loaderContext;
    }

    @Override
    public String toString() {
        return "WMUrlClassLoader{loaderContext='" + loaderContext + '\'' +"} ";
    }
}