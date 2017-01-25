/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util.utils;

import com.wavemaker.commons.util.ConversionUtils;
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

