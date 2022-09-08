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
package com.wavemaker.commons.util.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.wavemaker.commons.util.StringUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Simon Toens
 */
public class StringUtilsTest{
    @Test
    public void toJavaIdentifier1Test() {
        String s = StringUtils.toJavaIdentifier("1234", '_');
        assertEquals("_1234", s);
    }
    @Test
    public void toJavaIdentifier2Test() {
        String s = StringUtils.toJavaIdentifier("import", '_');
        assertEquals("_import", s);
    }
    @Test
    public void toJavaIdentifier3Test() {
        String s = StringUtils.toJavaIdentifier("my&name", '_');
        assertEquals("my_name", s);
    }
    @Test
    public void split1Test() {
        List<String> l = StringUtils.split("a,b,c");
        assertTrue(l.size() == 3);
        assertTrue(l.get(0).equals("a"));
        assertTrue(l.get(1).equals("b"));
        assertTrue(l.get(2).equals("c"));
    }
    @Test
    public void split2Test() {
        List<String> l = StringUtils.split("a,d:{a:b,c:d},c");
        assertTrue(l.size() == 3);
        assertTrue(l.get(0).equals("a"));
        assertTrue(l.get(1).equals("d:{a:b,c:d}"));
        assertTrue(l.get(2).equals("c"));
    }
    @Test
    public void split3Test() {
        List<String> l = StringUtils.split("a,d:{a:b,c:d},'c,d,e',f");
        assertTrue(l.size() == 4);
        assertTrue(l.get(0).equals("a"));
        assertTrue(l.get(1).equals("d:{a:b,c:d}"));
        assertTrue(l.get(2).equals("'c,d,e'"));
        assertTrue(l.get(3).equals("f"));
    }
    @Test
    public void split4Test() {
        List<String> l = StringUtils.split("{}, {}");
        assertTrue(l.size() == 2);
        assertTrue(l.get(0).equals("{}"));
        assertTrue(l.get(1).equals("{}"));
    }
    @Test
    public void split5Test() {
        List<String> l = StringUtils.split("{}, {}");
        assertTrue(l.size() == 2);
        assertTrue(l.get(0).equals("{}"));
        assertTrue(l.get(1).equals("{}"));
    }
    @Test
    public void split6Test() {
        List<String> l = StringUtils.split("nKDv8_LV34F3PdYFDoVQCgoCPmQVN7N0nvLypL26TuY6tA0MR5E.9CqtY7QEkn64,301 Howard,San Francisco,CA");
        assertTrue(l.size() == 4);
        assertTrue(l.get(0).equals("nKDv8_LV34F3PdYFDoVQCgoCPmQVN7N0nvLypL26TuY6tA0MR5E.9CqtY7QEkn64"));
        assertTrue(l.get(1).equals("301 Howard"));
        assertTrue(l.get(2).equals("San Francisco"));
        assertTrue(l.get(3).equals("CA"));
    }
    @Test
    public void splitListTest() {
        List<String> l = StringUtils.split("[a,b,c]");
        assertEquals(1, l.size());
        assertEquals("[a,b,c]", l.get(0));
    }
    @Test
    public void toFieldNameTest() throws Exception {
        Map<String, String> expectedResults = new LinkedHashMap<>();
        expectedResults.put("user", "user");
        expectedResults.put("User", "user");
        expectedResults.put("USER", "user");
        expectedResults.put("user_id", "userId");
        expectedResults.put("User_Id", "userId");
        expectedResults.put("User_id", "userId");
        expectedResults.put("USER_Id", "userId");
        expectedResults.put("USER_ID", "userId");

        for (final Map.Entry<String, String> entry : expectedResults.entrySet()) {
            assertEquals(entry.getValue(), StringUtils.toFieldName(entry.getKey()));
        }

    }

    @Test
    public void testHasUpperCase() {
        Assert.assertTrue(StringUtils.hasUpperCase("Abc"));
        Assert.assertFalse(StringUtils.hasUpperCase("abc"));
    }

    @Test
    public void testIsJavaKeyWord() {
        Assert.assertTrue(StringUtils.isJavaKeyword("Switch"));
    }

    @Test
    public void testGetItemsStartingWIth() {
        List<String> finalItems = StringUtils.getItemsStartingWith(Arrays.asList("sampleA","sampleB","sampleC"),"sample",true);
        String[] expectedList = {"A","B","C"};
        Assert.assertEquals(expectedList,finalItems.toArray());
    }

    @Test
    public void testGetFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String expectedDate = dateFormat.format(new Date());
        String actualDate = StringUtils.getFormattedDate();
        Assert.assertEquals(actualDate,expectedDate);

    }

    @Test
    public void testThrowableString() {
        Assert.assertTrue(StringUtils.toString(new RuntimeException("sample Exception")).contains("java.lang.RuntimeException: sample Exception"));
    }

    @Test
    public void testGetUniqueNames() {
        Assert.assertEquals(StringUtils.getUniqueName("abc",Arrays.asList("abc","bcd","abc2")),"abc3");
        Assert.assertEquals(StringUtils.getUniqueName("abc",Arrays.asList("bcd","cde")),"abc");
        Assert.assertEquals(StringUtils.getUniqueName("abc","abc"),"abc2");
    }

}