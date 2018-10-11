package com.wavemaker.commons.io;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wavemaker.commons.util.WMIOUtils;

public class DeleteTempFileOnCloseInputStreamTest {

    private File tempFile;
    private DeleteTempFileOnCloseInputStream deleteTempFileOnCloseInputStream;

    @BeforeTest
    public void beforeTest() throws FileNotFoundException {
        tempFile = WMIOUtils.createTempFile("test", ".txt");
        deleteTempFileOnCloseInputStream = new DeleteTempFileOnCloseInputStream(tempFile);
    }

    @Test
    public void testClose() throws IOException {
        deleteTempFileOnCloseInputStream.close();
        Assert.assertFalse(deleteTempFileOnCloseInputStream.getTempFile().exists());
    }

    @Test
    public void testFinalize() throws IOException {
        deleteTempFileOnCloseInputStream.finalize();
        Assert.assertFalse(deleteTempFileOnCloseInputStream.getTempFile().exists());
    }

    @Test
    public void testGetTempFile() {
        Assert.assertNotNull(deleteTempFileOnCloseInputStream.getTempFile());
        Assert.assertTrue(deleteTempFileOnCloseInputStream.getTempFile().getName().startsWith("test"));
    }

    @AfterTest
    public void afterTest() {
        tempFile.delete();
    }
}