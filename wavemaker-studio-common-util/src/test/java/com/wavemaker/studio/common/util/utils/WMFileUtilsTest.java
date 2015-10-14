package com.wavemaker.studio.common.util.utils;

import com.wavemaker.studio.common.util.WMFileUtils;
import org.testng.annotations.Test;

import java.io.*;
import java.util.UUID;

import static org.testng.Assert.*;

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
    }
}
