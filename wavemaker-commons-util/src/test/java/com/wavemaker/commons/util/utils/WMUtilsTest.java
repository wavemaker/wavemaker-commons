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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wavemaker.commons.util.WMUtils;

import static org.testng.Assert.assertEquals;


/*
 * Created by Prithvi Medavaram on 9/10/15.
 */

public class WMUtilsTest {
    @Test(dataProvider = "fileProvider")
    public void getFileExtensionsFromFileNameTest(String fileName, String extension) {
        assertEquals(WMUtils.getFileExtensionFromFileName(fileName), extension);
    }

    @Test(dataProvider = "uriProvider")
    public void decodeRequestURITest(String encoded, String decoded) {
        assertEquals(WMUtils.decodeRequestURI(encoded), decoded);
    }

    @DataProvider
    public Object[][] fileProvider() throws IOException {
        Object[][] objects = new Object[3][2];

        objects[0][0] = File.createTempFile("deleteIfYouSee", ".txt").getName();
        objects[0][1] = "txt";

        objects[1][0] = File.createTempFile("delete.If.You.;See", ".txt").getName();
        objects[1][1] = "txt";

        objects[2][0] = File.createTempFile("deleteIfYouSee", ".xml").getName();
        objects[2][1] = "xml";

        return objects;
    }

    @DataProvider
    public Object[][] uriProvider() {
        Object[][] objects = new Object[3][2];
        objects[0][0] = "https%3A%2F%2FWaveMaker%2FUtilClass%2FtestURL";
        objects[0][1] = "https://WaveMaker/UtilClass/testURL";

        objects[1][0] = "http%3A%2F%2Fwww.example.com%2F";
        objects[1][1] = "http://www.example.com/";

        objects[2][0] = "https%3A%2F%2Fwww.google.co.in%2Fwebhp%3Fsourceid%3Dchrome-instant%26ion%3D1%26espv%3D2%26ie%3DUTF-8%26client%3Dubuntu%23q%3Dwavemaker";
        objects[2][1] = "https://www.google.co.in/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8&client=ubuntu#q=wavemaker";
        return objects;
    }
}
