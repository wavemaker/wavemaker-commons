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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import com.wavemaker.commons.util.CastUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by Prithvi Medavaram on 13/10/15.
 */
public class CastUtilsTest {
    @Test
    public void castListTest(){
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");

        List unknownTypesList = stringList;
        List<String> castedList = CastUtils.cast(unknownTypesList);
        for (String i : castedList) {
            assertTrue(i instanceof String);
        }
        assertEquals(castedList, unknownTypesList);
    }

    @Test
    public void castSet(){
        Set<String> stringSet = new HashSet<>();
        stringSet.add("a");
        stringSet.add("b");
        stringSet.add("c");

        Set unknownTypesSet = stringSet;
        Set<String> castedSet = CastUtils.cast(unknownTypesSet);
        for(String i: castedSet){
            assertTrue(i instanceof String);
        }
        assertEquals(castedSet, unknownTypesSet);
    }

    @Test(expectedExceptions = {ClassCastException.class})
    public void castListClassCastExceptionTest(){
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");

        List unknownTypesList = stringList;
        List<Integer> castedList = CastUtils.cast(unknownTypesList);
        for (Integer i : castedList) {// this line should throw ClassCastException as we cannot iterate over integers in a string list
        }
    }

    @Test(expectedExceptions = {ClassCastException.class})
    public void castSetClassCastExceptionTest(){
        Set<String> stringSet = new HashSet<>();
        stringSet.add("a");
        stringSet.add("b");
        stringSet.add("c");

        Set unknownTypesList = stringSet;
        Set<Integer> castedSet = CastUtils.cast(unknownTypesList);
        for (Integer i : castedSet) {// this line should throw ClassCastException as we cannot iterate over integers in a string set
        }
    }

    @Test
    public void iteratorTest(){
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");

        Iterator iterator = stringList.iterator();
        Iterator unknownTypeIterator =iterator;
        Iterator<String> castedIterator = CastUtils.cast(unknownTypeIterator);
        assertNotNull(castedIterator);
        assertEquals("a", castedIterator.next());
        assertEquals("b", castedIterator.next());
        assertEquals("c", castedIterator.next());
        assertFalse(castedIterator.hasNext());
    }

}
