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
        Assert.assertTrue(tempFilesStorageManager.getFilePath(fileId).contains("null/auto-purge/"));
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