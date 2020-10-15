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
package com.wavemaker.commons.util.utils;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;

import com.wavemaker.commons.classloader.ClassLoaderUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Matt Small
 */
public class ClassLoaderUtilsTest {
    @Test
    public void tempClassLoader_getClassTest() throws Exception {

        File sourceJar = new ClassPathResource("com/wavemaker/commons/foojar.jar").getFile();
        File jar = File.createTempFile("tempClassLoader_getClassTest", ".jar");
        jar.deleteOnExit();
        FileUtils.copyFile(sourceJar, jar);

        try {
            ClassLoader cl = ClassLoaderUtils.getTempClassLoaderForFile(jar);
            Class<?> klass = ClassLoaderUtils.loadClass("foo.bar.baz.JarType", cl);
            assertNotNull(klass);
        } finally {
            jar.delete();
        }
    }

    @Test
    public void tempClassLoader_getResourceTest() throws Exception {

        File sourceJar = new ClassPathResource("com/wavemaker/commons/foojar.jar").getFile();
        File jar = File.createTempFile("tempClassLoader_getClassTest", ".jar");
        jar.deleteOnExit();
        FileUtils.copyFile(sourceJar, jar);

        try {
            ClassLoader cl = ClassLoaderUtils.getTempClassLoaderForFile(jar);
            InputStream is = ClassLoaderUtils.getResourceAsStream("foo/bar/baz/JarType.java", cl);
            assertNotNull(is);
            assertTrue(is.available() > 0);
            is.close();
        } finally {
            jar.delete();
        }
    }



    @Test
    public void getClassLoaderTest() throws ClassNotFoundException {
        ClassLoader loader = ClassLoaderUtils.getClassLoader();
        Class aClass = loader.loadClass("com.wavemaker.commons.util.utils.Car");
        assertNotNull(aClass);
        assertEquals(aClass, Car.class);
    }


}
