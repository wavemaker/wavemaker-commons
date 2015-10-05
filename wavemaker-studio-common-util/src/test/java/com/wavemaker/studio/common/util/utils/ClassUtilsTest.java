/**
 * Copyright (C) 2014 WaveMaker, Inc. All rights reserved.
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
package com.wavemaker.studio.common.util.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.wavemaker.infra.WMTestUtils;
import com.wavemaker.studio.common.util.ClassUtils;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * @author Matt Small
 */
public class ClassUtilsTest {

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
}