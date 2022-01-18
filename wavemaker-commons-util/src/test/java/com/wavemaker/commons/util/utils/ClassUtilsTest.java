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

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wavemaker.commons.util.ClassUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Matt Small
 */
public class ClassUtilsTest {

    private int testField = 10;

    @Test
    public void newInstanceTest() {
        Class<?> clazz = ClassUtilsTest.class;
        Object returned = ClassUtils.newInstance(clazz);
        assertTrue(returned instanceof ClassUtilsTest);
        assertEquals(((ClassUtilsTest) returned).testField, 10);

    }

    @Test(dataProvider = "propertyData")
    public void getPropertyGetterNameTest(String propertyName, String expectedGetterName) {
        assertEquals(ClassUtils.getPropertyGetterName(propertyName), expectedGetterName);
    }

    @Test(dataProvider = "propertyData")
    public void getAltPropertyGetterNameTest(String propertyName, String expectedAltPropertyGetterName) {
        assertEquals(ClassUtils.getAltPropertyGetterName(propertyName), expectedAltPropertyGetterName);
    }

    @Test(dataProvider = "propertyData")
    public void getPropertySetterNameTest(String propertyName, String expectedSetterName) {
        assertEquals(ClassUtils.getPropertySetterName(propertyName), expectedSetterName);
    }

    @DataProvider
    public Object[][] propertyData(Method name) {

        Object[][] obj = new Object[5][2];
        obj[0][0] = "User";
        obj[1][0] = "U";
        obj[2][0] = "u";
        obj[3][0] = "username";
        obj[4][0] = "userName";
        if (name.getName().equalsIgnoreCase("getPropertyGetterNameTest")) {
            obj[0][1] = "getUser";
            obj[1][1] = "getU";
            obj[2][1] = "getU";
            obj[3][1] = "getUsername";
            obj[4][1] = "getUserName";
        }
        if (name.getName().equalsIgnoreCase("getAltPropertyGetterNameTest")) {
            obj[0][1] = "isUser";
            obj[1][1] = "isU";
            obj[2][1] = "isU";
            obj[3][1] = "isUsername";
            obj[4][1] = "isUserName";
        }
        if (name.getName().equalsIgnoreCase("getPropertySetterNameTest")) {
            obj[0][1] = "setUser";
            obj[1][1] = "setU";
            obj[2][1] = "setU";
            obj[3][1] = "setUsername";
            obj[4][1] = "setUserName";
        }
        return obj;
    }
}