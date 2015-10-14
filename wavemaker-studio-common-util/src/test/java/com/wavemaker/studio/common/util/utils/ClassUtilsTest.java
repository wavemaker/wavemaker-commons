/**
 * Copyright (C) 2014 WaveMaker, Inc. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.studio.common.util.utils;

import com.wavemaker.studio.common.util.ClassUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Matt Small
 */
public class ClassUtilsTest {

    private int testField = 10;

    public ClassUtilsTest() {
    }

    public ClassUtilsTest(int testField) {
        this.testField = testField;
    }

    @Test
    public void getPublicMethodsTest() {

        boolean hadPrivateMethod = false;
        boolean hadPublicMethod_testPublic = false;
        boolean hadToString = false;
        boolean hadHashCode = false;

        List<Method> methods = ClassUtils.getPublicMethods(ClassUtilsObject.class);

        for (Method meth : methods) {
            if (meth.getName().equals("publicTest")) {
                hadPublicMethod_testPublic = true;
            } else if (meth.getName().equals("privateTest")) {
                hadPrivateMethod = true;
            } else if (meth.getName().equals("toString")) {
                hadToString = true;
            } else if (meth.getName().equals("hashCode")) {
                hadHashCode = true;
            }
        }

        assertTrue(hadPublicMethod_testPublic);
        assertFalse(hadPrivateMethod);
        assertFalse(hadToString);
        assertTrue(hadHashCode);
    }

    @Test
    public void getPublicFieldsTest() {

        List<Field> fields = ClassUtils.getPublicFields(ClassUtilsObject.class);

        boolean hadPublicField = false;
        boolean hadPrivateField = false;
        boolean hadFieldInherited = false;

        for (Field field : fields) {
            if (field.getName().equals("fieldPublic")) {
                hadPublicField = true;
            } else if (field.getName().equals("fieldPrivate")) {
                hadPrivateField = true;
            } else if (field.getName().equals("fieldInherited")) {
                hadFieldInherited = true;
            }
        }

        assertTrue(hadPublicField);
        assertFalse(hadPrivateField);
        assertTrue(hadFieldInherited);
    }

    @Test
    public void newInstanceTest() {
        Class<?> clazz = ClassUtilsTest.class;
        Object returned = ClassUtils.newInstance(clazz);
        assertTrue(returned instanceof ClassUtilsTest);
        assertEquals(((ClassUtilsTest)returned).testField, 10);

    }

    @Test(dataProvider = "propertyData")
    public void getPropertyGetterNameTest(String propertyName, String expectedGetterName, String expectedAltPropertyGetterName , String expectedSetterName) {
        assertEquals(ClassUtils.getPropertyGetterName(propertyName), expectedGetterName);
    }

    @Test(dataProvider = "propertyData")
    public void getAltPropertyGetterNameTest(String propertyName, String expectedGetterName, String expectedAltPropertyGetterName , String expectedSetterName) {
        assertEquals(ClassUtils.getAltPropertyGetterName(propertyName), expectedAltPropertyGetterName);
    }

    @Test(dataProvider = "propertyData")
    public void getPropertySetterNameTest(String propertyName, String expectedGetterName, String expectedAltPropertyGetterName , String expectedSetterName) {
        assertEquals(ClassUtils.getPropertySetterName(propertyName), expectedSetterName);
    }

    @DataProvider
    public Object[][] propertyData() {

        Object[][] obj = new Object[2][1];

        obj[0][0] = "User";
        obj[0][1] = "getUser";
        obj[0][1] = "isUser";
        obj[0][1] = "setUser";

        obj[1][0] = "u";
        obj[1][1] = "getU";
        obj[1][2] = "isU";
        obj[1][3] = "setU";

        obj[2][0] = "u";
        obj[2][1] = "getU";
        obj[1][2] = "isU";
        obj[1][3] = "setU";

        obj[3][0] = "username";
        obj[3][1] = "getUsername";
        obj[3][2] = "isUsername";
        obj[3][3] = "setUsername";

        obj[4][0] = "userName";
        obj[4][1] = "getUserName";
        obj[4][2] = "isUserName";
        obj[4][3] = "setUserName";
        return obj;
    }

    @Test
    public void getPropertyDescriptorsTest() throws Exception {
        //List<PropertyDescriptor> propertyDescriptorListExpected = new ArrayList<>();
        List<PropertyDescriptor> propertyDescriptorListActual;
        Class clazz = Car.class;

        propertyDescriptorListActual = ClassUtils.getPropertyDescriptors(clazz);

        assertEquals(propertyDescriptorListActual.size(), 2);
        Assert.assertEquals(propertyDescriptorListActual.get(0).getName(), "brand");
        Assert.assertEquals(propertyDescriptorListActual.get(1).getName(), "model");


    }
}