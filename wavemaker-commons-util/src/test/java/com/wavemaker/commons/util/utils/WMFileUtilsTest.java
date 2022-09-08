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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import org.testng.annotations.Test;

import com.wavemaker.commons.util.WMFileUtils;

import static org.testng.Assert.assertEquals;

/*
 * Created by prithvi on 12/10/15.
 */
public class WMFileUtilsTest {
    @Test
    public void readFileToStringTest() throws IOException {

        File testFileActual = File.createTempFile("testFile", ".tmp");
        String actualData= UUID.randomUUID().toString();

        PrintWriter printWriter = new PrintWriter(testFileActual,"UTF-8");
        printWriter.print(actualData);
        printWriter.close();

        String testFileExpectedData = WMFileUtils.readFileToString(testFileActual);
        assertEquals(actualData,testFileExpectedData);
        testFileActual.deleteOnExit();
    }

    @Test
    public void writeStringToFile() throws IOException{

        File testFileActual = File.createTempFile("testFile", ".tmp");
        String actualData = UUID.randomUUID().toString();

        WMFileUtils.writeStringToFile(testFileActual, actualData);

        String expectedData = WMFileUtils.readFileToString(testFileActual);

        assertEquals(actualData, expectedData);
        testFileActual.deleteOnExit();
    }
}
