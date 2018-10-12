package com.wavemaker.commons.io;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wavemaker.commons.util.WMIOUtils;

public class ReplaceOperationTest {

    private static String fromString = "old data";
    private static String toString = "new data";
    private File tempFile;

    @BeforeTest
    public void beforeTest() {
        tempFile = WMIOUtils.createTempFile("test", ".txt");
        tempFile.getContent().write(fromString);
    }

    @Test
    public void testPerform() {
        ReplaceOperation replaceOperation = new ReplaceOperation(fromString, toString);
        replaceOperation.perform(tempFile);
        Assert.assertEquals(tempFile.getContent().asString(), toString);
    }

    @AfterTest
    public void tearDown() {
        tempFile.delete();
    }
}