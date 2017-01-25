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

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;

import org.testng.annotations.Test;

import com.wavemaker.commons.util.JAXBUtils;
import com.wavemaker.commons.util.XMLWriter;

import static org.testng.Assert.assertEquals;

/**
 * Created by anitha on 19/10/15.
 */
public class XMLWriterTest {


    @Test
   public void xmlWriterTest() throws Exception {
        //JAXBContext jaxbContext = JAXBContext.newInstance();

        File tempFile = File.createTempFile("Sampletemp", ".xml");
        tempFile.deleteOnExit();
        PrintWriter pw = new PrintWriter (tempFile);
        XMLWriter xw= new XMLWriter(pw);
        xw.setTextOnSameLineAsParentElement(true);
        xw.addVersion();
        //xw.addDoctype("Test","SYSTEM","file:///tmp/");
        xw.addElement("customer");
        xw.addAttribute("id", "100");
        xw.addElement("name");
        xw.addText("Amanda", false);
        xw.closeElement();
        xw.addElement("age");
        xw.addText("20", false);
        xw.closeElement();
        xw.finish();

//        FileReader fileReader = new FileReader(temp);
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);

        FileReader fileReader = new FileReader(tempFile);
        Customer customer = JAXBUtils.unMarshall(jaxbContext, fileReader);
        assertEquals("Amanda\n  ", customer.getName());
        assertEquals(20, customer.getAge());


    }

    @Test(expectedExceptions = {RuntimeException.class})
    public void XmlWriterTestExceptionCase() throws Exception {
        File temp = File.createTempFile("Sampletemp", ".xml");
        PrintWriter pw = new PrintWriter(temp);
        XMLWriter xw = null;
        try {
            xw = new XMLWriter(pw);
            xw.addVersion();
            xw.addDoctype("Test", "SYSTEM", "file:///tmp/");
            xw.addElement("Employee");
            xw.addAttribute("Name", "chris");
            xw.addVersion();// This line should throw exception
        } finally {
            xw.finish();
        }
    }



}
