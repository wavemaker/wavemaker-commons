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
package com.wavemaker.commons.io;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wavemaker.commons.util.WMIOUtils;

public class BulkReplaceOperationTest {

    private static String fromString = "this data has to be modified";
    private static String toString = "this content has  been modified";
    private File tempFile;

    @BeforeTest
    public void beforeTest() {
        tempFile = WMIOUtils.createTempFile("test", ".txt");
        tempFile.getContent().write(fromString);
    }

    @Test
    public void testPerform() {
        BulkReplaceOperation bulkReplaceOperation = new BulkReplaceOperation();
        bulkReplaceOperation.add("data", "content");

        Map<String, String> testMap = new HashMap<>();
        testMap.put("to", "");
        testMap.put("be", "been");

        bulkReplaceOperation.addAll(testMap);
        bulkReplaceOperation.perform(tempFile);
        Assert.assertEquals(tempFile.getContent().asString(), toString);
    }

    @AfterTest
    public void afterTest() {
        tempFile.delete();
    }
}