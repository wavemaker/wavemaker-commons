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
package com.wavemaker.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SortedListTest {


    @Test
    public void testAdd() {
        List<String> delegate = new ArrayList<>(Arrays.asList("c", "d", "b"));
        SortedList<String> sortedList = new SortedList<>(delegate);
        sortedList.add("a");
        String[] expected = {"a","b","c","d"};
        Assert.assertEquals(expected,sortedList.toArray());
    }

    @Test
    public void testAddAll() {
        List<String> delegate = new ArrayList<>(Arrays.asList("c", "d", "b"));
        SortedList<String> sortedList = new SortedList<>(delegate);
        sortedList.addAll(Arrays.asList("a","e","f"));
        String[] expected = {"a","b","c","d","e","f"};
        Assert.assertEquals(expected,sortedList.toArray());
    }
}