package com.wavemaker.studio.common.util.utils;

import com.wavemaker.studio.common.util.ConversionUtils;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;


/**
 * Created by prithvit on 15/10/15.
 */
public class ConversionUtilsTest {

    @Test
    public void convertToResourceListTest() throws Exception{
        List<File> fileList = new ArrayList<>();
        File file1 =File.createTempFile("file1", ".txt");
        File file2 = File.createTempFile("file2", ".txt");
        File file3 = Files.createTempDirectory("file3").toFile();

        try{

        fileList.add(file1);
        fileList.add(file2);
        fileList.add(file3);
        List<Resource> resources = ConversionUtils.convertToResourceList(fileList);
        assertEquals(resources.size(), fileList.size());
        assertEquals(resources.get(0).getFilename(), file1.getName());
        assertEquals(resources.get(1).getFilename(), file2.getName());
        assertEquals(resources.get(2).getFilename(), file3.getName());
        }
        finally {
            file1.delete();
            file2.delete();
            file3.delete();
        }
    }
}

