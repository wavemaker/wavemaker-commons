package com.wavemaker.commons.json;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.util.WMIOUtils;

public class JSONUtilsTest {

    @Test
    public void testReadTreeValid() {
        String jsonData = "{\"id\" : \"sampleId\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes());
        JsonNode resultantJson = JSONUtils.readTree(inputStream);
        Assert.assertEquals("sampleId", resultantJson.get("id").asText());
        resultantJson = JSONUtils.readTree(jsonData);
        Assert.assertEquals("sampleId", resultantJson.get("id").asText());

    }

    @Test(expectedExceptions = WMRuntimeException.class)
    public void testReadTreeInValid() {
        String jsonData = "{\"id\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes());
        JSONUtils.readTree(inputStream);

    }

    @Test(expectedExceptions = WMRuntimeException.class)
    public void testReadTreeInValid2() {
        String jsonData = "{\"id\"}";
        JSONUtils.readTree(jsonData);

    }

    @Test
    public void testPrettifyJson() throws IOException {
        String jsonData = "{\"id\" : \"sampleId\"}";
        File tmpFile = new File("/tmp/tmpFile.json");
        JSONUtils.prettifyJSON(jsonData, tmpFile);
        WMIOUtils.deleteFile(tmpFile);
        JSONUtils.prettifyJSON("");
    }

    @Test(expectedExceptions = WMRuntimeException.class)
    public void testPrettifyJsonInvalid() throws IOException {
        File tmpFile = new File("/tmp/tmpFile.json");
        JSONUtils.prettifyJSON(null, tmpFile);
    }

    @Test
    public void testRegisterModule() {
        JSONUtils.registerModule(new SimpleModule());
    }

}