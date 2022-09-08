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

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wavemaker.commons.util.WMIOUtils;

public class LatestLastModifiedTest {

    private LatestLastModified latestLastModified;
    private File file;

    @BeforeTest
    public void beforeTest() {
        file = WMIOUtils.createTempFile("temp", ".txt");
        latestLastModified = new LatestLastModified();
    }

    @Test
    public void testPerform() {
        latestLastModified.perform(file);
        Assert.assertNotEquals(latestLastModified.getValue(), 0);
    }

    @AfterTest
    public void afterTest() {
        file.delete();
    }

}