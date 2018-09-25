/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
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
package com.wavemaker.commons.util.utils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wavemaker.commons.util.ObjectAccess;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by Prithvi Medavaram on 13/10/15.
 */
public class ObjectAccessTest {

    @Test
    public void newInstanceForClassName() {
        String className = Car.class.getName();
        Object testObject = ObjectAccess.getInstance().newInstance(className);
        assertTrue(testObject instanceof Car);
    }

    @Test
    public void newInstanceForClass() {
        Class<?> clazz = Car.class;
        Object testObject = ObjectAccess.getInstance().newInstance(clazz);
        assertTrue(testObject instanceof Car);
    }

    @Test
    public void forNameTest() {
        String className = Car.class.getName();
        Class testClass = ObjectAccess.getInstance().forName(className);
        assertEquals(Car.class, testClass);
    }


    @Test
    public void invokeTest() {
        Car car = new Car();
        String randomString = UUID.randomUUID().toString();
        car.setBrand(randomString);
        Object brand = ObjectAccess.getInstance().invoke(car, "getBrand");
        assertEquals(randomString, brand);
    }

    @Test
    public void getPropertyTest() {
        Car car = new Car();
        String brandName = UUID.randomUUID().toString();
        car.setBrand(brandName);
        String propertyName = "brand";
        Object property = ObjectAccess.getInstance().getProperty(car, propertyName);
        assertEquals(property, brandName);
    }

    @Test
    public void setPropertyTest() {
        Car car = new Car();
        String brandName = UUID.randomUUID().toString();
        String propertyName = "brand";
        ObjectAccess.getInstance().setProperty(car, propertyName, brandName);
        assertEquals(car.getBrand(), brandName);
    }

    @Test
    public void getPropertyTypeTest() {

        Class clazz = Car.class;
        String propertyName = "model";
        Class actualClass = ObjectAccess.getInstance().getPropertyType(clazz, propertyName);
        assertEquals(actualClass, propertyName.getClass());

    }

    @Test
    public void hasMethodTest() {
        Class klazz = Car.class;
        String methodName = "getBrand";

        assertTrue(ObjectAccess.getInstance().hasMethod(klazz, methodName, 0));
        assertFalse(ObjectAccess.getInstance().hasMethod(klazz, methodName, 1) ||
                ObjectAccess.getInstance().hasMethod(klazz, methodName, 2) ||
                ObjectAccess.getInstance().hasMethod(klazz, methodName, 3));


        String method2 = "setBrand";

        assertTrue(ObjectAccess.getInstance().hasMethod(klazz, method2, 1));
    }

    @Test
    public void getPropertiesTest() throws Exception {
        Class clazz = Car.class;
        Map<String, Class<?>> actualMap = ObjectAccess.getInstance().getProperties(clazz);
        assertTrue(actualMap.size() == 3);
        assertTrue(actualMap.get("model") == String.class);
        assertTrue(actualMap.get("price") == int.class);
        assertTrue(actualMap.get("brand") == String.class);
    }

    @Test
    public void objectToStringTest() {

        Car car = new Car();
        String actualString = ObjectAccess.getInstance().objectToString(car);
        assertNotNull(actualString.trim());
        assertTrue(actualString.endsWith("}"));
        assertTrue(actualString.contains("model"));
        assertTrue(actualString.contains("brand"));
        assertTrue(actualString.contains("price"));
        assertTrue(actualString.contains("Car:{"));
    }

    @Test
    public void testGenericReturntypes() {
        Class[] expectedClasses = {Car.class};
        Assert.assertEquals(ObjectAccess.getInstance().getGenericReturnTypes(Task.class,"getTasks",0).toArray(),
                expectedClasses);

    }

    @Test
    public void testGetMethodReturnType() {
        Assert.assertEquals(ObjectAccess.getInstance().getMethodReturnType(List.class,"size",0),
                int.class);
    }

    @Test
    public void testGetParameterTypes() {
        Assert.assertEquals(ObjectAccess.getInstance().getMethodParamTypes(Task.class,"setTasks",1).toArray()[0],
                List.class);
    }

    @Test
    public void testHasAnnotation() {
        Assert.assertTrue(ObjectAccess.getInstance().hasAnnotation(MyAnnotation.class, new Task(), "getTasks"));
        Assert.assertFalse(ObjectAccess.getInstance().hasAnnotation(MyAnnotation.class, new Task(), "tasks"));
    }


}