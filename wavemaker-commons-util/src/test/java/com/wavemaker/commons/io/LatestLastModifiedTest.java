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