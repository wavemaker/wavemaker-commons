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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.util.ObjectUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Simon Toens
 */
public class ObjectUtilsTest {
    @SuppressWarnings("unchecked")
    @Test
    public void toArrayTest() {
        List l = new ArrayList(3);
        l.add("s1");
        l.add("s2");
        l.add("s3");
        String s[] = (String[]) ObjectUtils.toArray(l, String.class);
        assertTrue(s[0].equals("s1"));
        assertTrue(s[1].equals("s2"));
        assertTrue(s[2].equals("s3"));
    }

    @Test
    public void addArraysTest() {
        String[] s1 = {"s1", "s2", "s3"};
        String[] s2 = {"s4", "s5", "s6"};
        String[] s3 = {"s7", "s8", "s9"};

        String[] all = (String[]) ObjectUtils.addArrays(s1, s2, s3);

        assertTrue(all.length == 9);

        for (int i = 0; i < 9; i++) {
            assertTrue(all[i].equals("s" + (i + 1)));
        }
    }

    @Test
    public void getArrayTypeArrayTest() {

        int[] is = new int[]{1, 2, 3};
        Integer[] Is = new Integer[]{1, 2, 3};
        String[] ss = new String[]{"1", "2", "3"};

        assertEquals(int.class, ObjectUtils.getArrayType(is));
        assertEquals(Integer.class, ObjectUtils.getArrayType(Is));
        assertEquals(String.class, ObjectUtils.getArrayType(ss));
    }

    @Test
    public void getArrayTypeCollectionTest() {

        List<Integer> Is = new ArrayList<>();
        Is.add(1);
        Is.add(2);

        List<Object> Is2 = new ArrayList<>();
        Is2.add(1);
        Is2.add(2);

        List<Object> mm = new ArrayList<>();
        mm.add("foo");
        mm.add(1);

        Object[] os = new Object[2];
        os[0] = "hi";
        os[1] = 12;

        assertEquals(Integer.class, ObjectUtils.getArrayType(Is));
        assertEquals(Integer.class, ObjectUtils.getArrayType(Is2));
        assertEquals(null, ObjectUtils.getArrayType(mm));
        assertEquals(null, ObjectUtils.getArrayType(os));
    }

    @Test
    public void getArrayTypeExceptionTest() {

        boolean gotException = false;

        try {
            ObjectUtils.getArrayType(null);
        } catch (IllegalArgumentException e) {
            gotException = true;
        }
        assertTrue(gotException);
    }

    @Test
    public void getIdTest() {
        Car car = new Car();
        String objectId = ObjectUtils.getId(car);
        assertNotNull(objectId);
        String expectedString = "com.wavemaker.commons.util.utils.Car";
        String actualString = ObjectUtils.getId(car);
        String actualStringOmittingAddress = actualString.substring(0, actualString.indexOf("@"));
        assertEquals(actualStringOmittingAddress, expectedString);
    }

    @Test()
    public void isNullOrEmptyStringTest() {

        assertTrue(ObjectUtils.isNullOrEmpty(""));
        assertTrue(ObjectUtils.isNullOrEmpty("\t"));
        assertFalse(ObjectUtils.isNullOrEmpty(UUID.randomUUID().toString()));

    }

    @Test
    public void toStringForObjectTest() {
        Object[] array = new Object[3];
        array[0] = "1";
        array[1] = true;
        array[2] = 1;

        String actualString = ObjectUtils.toString(array);
        assertEquals(actualString, "1, true, 1");
    }

    @Test
    public void toStringWithOtherSeparatorTest() {
        Object[] array = new Object[3];
        array[0] = "1";
        array[1] = true;
        array[2] = 1;

        String sep = "; ";
        String actualString = ObjectUtils.toString(array, sep);
        assertEquals(actualString, "1; true; 1");
    }

    @Test
    public void toStringForCollection() {

        Collection collection = new ArrayList<>();
        String sep = ", ";
        collection.add(1);
        collection.add("one");
        collection.add(true);

        String actualString = ObjectUtils.toString(collection, sep);
        assertEquals(actualString, "1, one, true");
    }

    @Test
    public void testGetKeysStartingWIthPrefix() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("sampleCase1", "case1");
        testMap.put("sampleCase2", "case2");
        testMap.put("Case3", "case3");
        String[] expected = {"sampleCase1", "sampleCase2"};
        Assert.assertEqualsNoOrder(ObjectUtils.getKeysStartingWith("sample", testMap).toArray(), expected);
    }

    @Test
    public void testStrcmp() {
        Assert.assertFalse(ObjectUtils.strCmp(new Car(), new Customer()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDiffObjectException() {
        ObjectUtils.diffObjects(new Car(), new Customer());
    }

    @Test
    public void testDiffObjects() {
        Car car1 = new Car();
        Car car2 = new Car();
        car1.setPrice(1000);
        car2.setPrice(2000);
        Assert.assertEquals(ObjectUtils.diffObjects(car1, car2), "getPrice: 1000 != 2000\n");
    }

    @Test
    public void testMap() {
        Car car1 = new Car("ab", "xv", 2000);
        Car car2 = new Car("ac", "xl", 3000);
        String[] funcNames = {"getPrice"};
        List<Car> carsList = new ArrayList<>();
        carsList.add(car1);
        carsList.add(car2);
        int[] expectedList = {2000, 3000};
        Assert.assertEquals(ObjectUtils.map(funcNames, carsList).toArray(), expectedList);
    }

    @Test(expectedExceptions = WMRuntimeException.class)
    public void testExceptionMap() {
       String[] funcNames = {"noMethod"};
       List<Car> carsList = new ArrayList<>();
       carsList.add(new Car());
       ObjectUtils.map(funcNames,carsList);
    }

    @Test(expectedExceptions = WMRuntimeException.class)
    public void testException2() {
        String[] funcNames = {"setPrice"};
        List<Car> carsList = new ArrayList<>();
        carsList.add(new Car("ab","xv",2000));
        ObjectUtils.map(funcNames, carsList);

    }
}
