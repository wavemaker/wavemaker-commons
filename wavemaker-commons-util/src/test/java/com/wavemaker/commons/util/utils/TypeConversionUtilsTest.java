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
package com.wavemaker.commons.util.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import bsh.Primitive;
import com.wavemaker.commons.util.TypeConversionUtils;
import junit.framework.TestCase;
import static org.testng.Assert.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Simon Toens
 */
public class TypeConversionUtilsTest{

    @Test
    public void isPrimitiveTest() {

        TestCase.assertFalse(TypeConversionUtils.isPrimitiveOrWrapper(Object.class));
        assertFalse(TypeConversionUtils.isPrimitiveOrWrapper(ClassUtilsObject.class));

        assertTrue(TypeConversionUtils.isPrimitiveOrWrapper(int.class));
        assertTrue(TypeConversionUtils.isPrimitiveOrWrapper(Integer.class));
        assertTrue(TypeConversionUtils.isPrimitiveOrWrapper(AtomicLong.class));
        assertTrue(TypeConversionUtils.isPrimitiveOrWrapper("a".getClass()));
    }
    @Test
    public void isArrayTest() {

        assertTrue(TypeConversionUtils.isArray(Object[].class));
        assertFalse(TypeConversionUtils.isArray(int.class));
        assertTrue(TypeConversionUtils.isArray(ArrayList.class));
        assertFalse(TypeConversionUtils.isArray(HashMap.class));
    }
    @Test
    public void isMapTest() {

        assertFalse(TypeConversionUtils.isMap(Object[].class));
        assertFalse(TypeConversionUtils.isMap(int.class));
        assertFalse(TypeConversionUtils.isMap(ArrayList.class));
        assertTrue(TypeConversionUtils.isMap(HashMap.class));
    }
    @Test
    public void fromStringTest() {

        Byte b = (Byte) TypeConversionUtils.fromString(Byte.class, "1");
        assertTrue(b.equals(Byte.valueOf("1")));
        b = (Byte) TypeConversionUtils.fromString(byte.class, "1");
        assertTrue(b.equals(Byte.valueOf("1")));

        Boolean bb = (Boolean) TypeConversionUtils.fromString(Boolean.class, "true");
        assertTrue(bb.equals(Boolean.TRUE));
        bb = (Boolean) TypeConversionUtils.fromString(boolean.class, "true");
        assertTrue(bb.equals(Boolean.TRUE));

        Date da = (Date) TypeConversionUtils.fromString(Date.class, "1191607354000");
        assertTrue(da.getTime() == 1191607354000L);

        da = (Date) TypeConversionUtils.fromString(Date.class, "-1191607354000");
        assertTrue(da.getTime() == -1191607354000L);

        Double d = (Double) TypeConversionUtils.fromString(Double.class, "1.2");
        assertTrue(d.equals(Double.valueOf("1.2")));
        d = (Double) TypeConversionUtils.fromString(double.class, "1.2");
        assertTrue(d.equals(Double.valueOf("1.2")));

        Float f = (Float) TypeConversionUtils.fromString(Float.class, "1.3");
        assertTrue(f.equals(Float.valueOf("1.3")));
        f = (Float) TypeConversionUtils.fromString(float.class, "1.3");
        assertTrue(f.equals(Float.valueOf("1.3")));

        Integer i = (Integer) TypeConversionUtils.fromString(Integer.class, "5");
        assertTrue(i.equals(Integer.valueOf("5")));
        i = (Integer) TypeConversionUtils.fromString(int.class, "5");
        assertTrue(i.equals(Integer.valueOf("5")));

        Long l = (Long) TypeConversionUtils.fromString(Long.class, "7");
        assertTrue(l.equals(Long.valueOf("7")));
        l = (Long) TypeConversionUtils.fromString(long.class, "7");
        assertTrue(l.equals(Long.valueOf("7")));

        Short s = (Short) TypeConversionUtils.fromString(Short.class, "6");
        assertTrue(s.equals(Short.valueOf("6")));
        s = (Short) TypeConversionUtils.fromString(short.class, "6");
        assertTrue(s.equals(Short.valueOf("6")));

        String ss = (String) TypeConversionUtils.fromString(String.class, "s");
        assertTrue(ss.equals("s"));
    }
    @Test
    public void primitivesMatchTest() {

        assertTrue(TypeConversionUtils.primitivesMatch(boolean.class, Boolean.class));
        assertTrue(TypeConversionUtils.primitivesMatch(Boolean.class, boolean.class));

        assertTrue(TypeConversionUtils.primitivesMatch(byte.class, Byte.class));
        assertTrue(TypeConversionUtils.primitivesMatch(Byte.class, byte.class));

        assertTrue(TypeConversionUtils.primitivesMatch(double.class, Double.class));
        assertTrue(TypeConversionUtils.primitivesMatch(Double.class, double.class));

        assertTrue(TypeConversionUtils.primitivesMatch(float.class, Float.class));
        assertTrue(TypeConversionUtils.primitivesMatch(Float.class, float.class));

        assertTrue(TypeConversionUtils.primitivesMatch(int.class, Integer.class));
        assertTrue(TypeConversionUtils.primitivesMatch(Integer.class, int.class));

        assertTrue(TypeConversionUtils.primitivesMatch(long.class, Long.class));
        assertTrue(TypeConversionUtils.primitivesMatch(Long.class, long.class));

        assertTrue(TypeConversionUtils.primitivesMatch(short.class, Short.class));
        assertTrue(TypeConversionUtils.primitivesMatch(Short.class, short.class));
        assertTrue(TypeConversionUtils.primitivesMatch(char.class, Character.class));
        assertTrue(TypeConversionUtils.primitivesMatch(Character.class, char.class));


    }

    @Test
    public void isServletClassTest(){
        assertTrue(TypeConversionUtils.isServletClass("HttpServletRequest"));
        assertTrue(TypeConversionUtils.isServletClass("HttpServletResponse"));
        assertTrue(TypeConversionUtils.isServletClass("MultipartHttpServletRequest"));
        assertFalse(TypeConversionUtils.isServletClass(UUID.randomUUID().toString()));
    }

    @Test(dataProvider = "primitiveDataTypes")
    public void primitiveForNameTest(String className){
        Class clazz= TypeConversionUtils.primitiveForName(className);
        assertNotNull(clazz);
    }

    @DataProvider(name="primitiveDataTypes")
    public Object[][] primitiveDataTypes(){
        Object[][] obj = new Object[7][1];
        obj[0][0]="boolean";
        obj[1][0]="short";
        obj[2][0]="int";
        obj[3][0]="char";
        obj[4][0]="float";
        obj[5][0]="double";
        obj[6][0]="byte";
        return obj;
    }

    @Test
    public void primitiveForNameInvalidTypesTest(){
        Class clazz= TypeConversionUtils.primitiveForName(UUID.randomUUID().toString());
        assertNull(clazz);
    }

}