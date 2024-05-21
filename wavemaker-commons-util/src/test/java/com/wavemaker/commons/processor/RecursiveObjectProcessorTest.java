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

package com.wavemaker.commons.processor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RecursiveObjectProcessorTest {
    private static final int PROCESSED_INT = 100;
    private static final String PROCESSED_STRING = "processedString";

    private RecursiveObjectProcessor recursiveObjectProcessor;

    @BeforeMethod
    public void init() {
        TestObjectProcessor testObjectProcessor = new TestObjectProcessor();
        ProcessContext processContext = new ProcessContext();
        ChildObjectRetriever childObjectRetriever = new DefaultChildObjectRetriever();
        recursiveObjectProcessor = new RecursiveObjectProcessor(testObjectProcessor, childObjectRetriever, processContext);
    }

    @Test
    public void testString() {
        Object processedString = recursiveObjectProcessor.processRootObject("string");
        Assert.assertEquals(processedString, PROCESSED_STRING);
    }

    @Test
    public void testList() {
        Object processedList = recursiveObjectProcessor.processRootObject(List.of("string1", "string2"));
        Assert.assertEquals(processedList, List.of(PROCESSED_STRING, PROCESSED_STRING));
    }

    @Test
    public void testMap() {
        Object processedMap = recursiveObjectProcessor.processRootObject(Map.of("k1", "v1"));
        Assert.assertEquals(processedMap, Map.of(PROCESSED_STRING, PROCESSED_STRING));
    }

    @Test
    public void testSet() {
        Object processedSet = recursiveObjectProcessor.processRootObject(Set.of("string1"));
        Assert.assertEquals(processedSet, Set.of(PROCESSED_STRING));
    }

    @Test
    public void testArray() {
        Object processedArray = recursiveObjectProcessor.processRootObject(new String[]{"string1", "string2"});
        Assert.assertEquals(processedArray, new String[]{PROCESSED_STRING, PROCESSED_STRING});
    }

    @Test
    public void testPrimitivePojo() {
        PrimitivePojo primitivePojo = new PrimitivePojo(1, 1, "data");
        PrimitivePojo expectedPrimitivePojo = new PrimitivePojo(PROCESSED_INT, PROCESSED_INT, PROCESSED_STRING);
        Object processedPrimitivePojo = recursiveObjectProcessor.processRootObject(primitivePojo);
        Assert.assertEquals(processedPrimitivePojo, expectedPrimitivePojo);
    }

    @Test
    public void testListPojo() {
        List<String> stringList = List.of("string1", "string2");
        List<List<String>> nestedStringList = List.of(List.of("string3", "string4"), List.of("string5", "string6"));
        List<PrimitivePojo> primitivePojoList = List.of(new PrimitivePojo(1, 1, "data"));
        ListPojo listPojo = new ListPojo(stringList, nestedStringList, primitivePojoList);
        Object processedListPojo = recursiveObjectProcessor.processRootObject(listPojo);
        ListPojo expectedListPojo = new ListPojo(List.of(PROCESSED_STRING, PROCESSED_STRING), List.of(List.of(PROCESSED_STRING, PROCESSED_STRING),
            List.of(PROCESSED_STRING, PROCESSED_STRING)), List.of(new PrimitivePojo(PROCESSED_INT, PROCESSED_INT, PROCESSED_STRING)));
        Assert.assertEquals(processedListPojo, expectedListPojo);
    }

    @Test
    public void testMapPojo() {
        Map<String, String> stringMap = Map.of("k1", "v1");
        Map<String, Map<String, String>> nestedStringMap = Map.of("string1", Map.of("k2", "v2"));
        Map<PrimitivePojo, PrimitivePojo> primitivePojoMap = Map.of(new PrimitivePojo(1, 1, "data"),
            new PrimitivePojo(1, 1, "data"));
        MapPojo mapPojo = new MapPojo(stringMap, nestedStringMap, primitivePojoMap);
        Object processedMapPojo = recursiveObjectProcessor.processRootObject(mapPojo);
        MapPojo expectedMapPojo = new MapPojo(Map.of(PROCESSED_STRING, PROCESSED_STRING), Map.of(PROCESSED_STRING,
            Map.of(PROCESSED_STRING, PROCESSED_STRING)), Map.of(new PrimitivePojo(PROCESSED_INT, PROCESSED_INT, PROCESSED_STRING),
            new PrimitivePojo(PROCESSED_INT, PROCESSED_INT, PROCESSED_STRING)));
        Assert.assertEquals(processedMapPojo, expectedMapPojo);
    }

    @Test
    public void testSetPojo() {
        Set<String> stringSet = Set.of("string1");
        Set<Set<String>> nestedStringSet = Set.of(Set.of("string2"));
        SetPojo setPojo = new SetPojo(stringSet, nestedStringSet);
        Object processedSetPojo = recursiveObjectProcessor.processRootObject(setPojo);
        SetPojo expectedSetPojo = new SetPojo(Set.of(PROCESSED_STRING), Set.of(Set.of(PROCESSED_STRING)));
        Assert.assertEquals(processedSetPojo, expectedSetPojo);
    }

    @Test
    public void testArrayPojo() {
        ArrayPojo arrayPojo = new ArrayPojo(new int[]{1, 2}, new Integer[]{1, 2}, new String[]{"string1", "string2"},
            new PrimitivePojo[]{new PrimitivePojo(1, 1, "string3")});
        Object processedArrayPojo = recursiveObjectProcessor.processRootObject(arrayPojo);
        ArrayPojo expectedArrayPojo = new ArrayPojo(new int[]{PROCESSED_INT, PROCESSED_INT}, new Integer[]{PROCESSED_INT, PROCESSED_INT},
            new String[]{PROCESSED_STRING, PROCESSED_STRING}, new PrimitivePojo[]{new PrimitivePojo(PROCESSED_INT, PROCESSED_INT, PROCESSED_STRING)});
        Assert.assertEquals(processedArrayPojo, expectedArrayPojo);
    }
}
