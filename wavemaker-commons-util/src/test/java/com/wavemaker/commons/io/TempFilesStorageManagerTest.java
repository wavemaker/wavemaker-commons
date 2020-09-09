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
package com.wavemaker.commons.io;

import java.io.ByteArrayInputStream;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TempFilesStorageManagerTest {

    private TempFilesStorageManager tempFilesStorageManager;
    private String fileId;

    @BeforeTest
    public void beforeTest() {
        tempFilesStorageManager = new TempFilesStorageManager();
        fileId = tempFilesStorageManager.registerNewFile("sample.txt");
    }

    @Test
    public void testRegisterNewFile() {
        String tempFieldId = tempFilesStorageManager.registerNewFile(new ByteArrayInputStream("some data".getBytes()), "sample1.txt");
        Assert.assertEquals(tempFilesStorageManager.getFileName(tempFieldId), "sample1.txt");
        tempFilesStorageManager.deRegister(tempFieldId);
    }

    @Test
    public void testGetFileName() {
        Assert.assertEquals(tempFilesStorageManager.getFileName(fileId),"sample.txt");
    }

    @Test
    public void testGetFilePath() {
        Assert.assertTrue(tempFilesStorageManager.getFilePath(fileId).startsWith("/tmp/auto-purge/"));

        System.setProperty("wm.studio.temp.dir", "/tmp/wm_temp");

        TempFilesStorageManager tmpFSM = new TempFilesStorageManager();
        final String newFileId = tmpFSM.registerNewFile("/sample.txt");

        Assert.assertFalse(tmpFSM.getFilePath(newFileId).startsWith("/tmp/auto-purge/"));
        Assert.assertTrue(tmpFSM.getFilePath(newFileId).startsWith("/tmp/wm_temp/auto-purge/"));

        System.clearProperty("wm.studio.temp.dir");
    }

    @Test
    public void testGetFileInputStream() {
        Assert.assertNotNull(tempFilesStorageManager.getFileInputStream(fileId));
    }

    @Test
    public void testGetFileOutputStream() {
        Assert.assertNotNull(tempFilesStorageManager.getFileOutputStream(fileId));
    }

    @Test
    public void testGetUniqueDirectory() {
        Assert.assertTrue(tempFilesStorageManager.getFilePath(fileId).contains("/sample.txt"));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testDeRegister() {
        String tempFieldId = tempFilesStorageManager.registerNewFile(new ByteArrayInputStream("some data".getBytes()), "sample2.txt");
        tempFilesStorageManager.deRegister(tempFieldId);
        tempFilesStorageManager.getFileName(tempFieldId);
    }

    @AfterTest
    public void afterTest() {
        tempFilesStorageManager.deRegister(fileId);
    }
}