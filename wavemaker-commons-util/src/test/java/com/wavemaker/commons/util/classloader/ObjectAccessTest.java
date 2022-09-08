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
package com.wavemaker.commons.util.classloader;

import org.testng.annotations.Test;

import com.wavemaker.commons.util.ObjectAccess;
import com.wavemaker.commons.util.utils.Car;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by Prithvi Medavaram on 13/10/15.
 */
public class ObjectAccessTest {

    @Test
    public void newInstanceForClassObjectTest() {
        ObjectAccess objectAccess = ObjectAccess.getInstance();
        Class clazz = Car.class;
        Object expectedObject = objectAccess.newInstance(clazz);
        assertTrue(expectedObject instanceof Car);
    }

    @Test
    public void forNameTest() {
        ObjectAccess objectAccess = ObjectAccess.getInstance();
        String className = com.wavemaker.commons.util.utils.Car.class.getName();
        Class<?> clazz = objectAccess.forName(className);
        assertEquals(Car.class, clazz);
    }
}

