/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.util.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.wavemaker.commons.util.JAXBUtils;
import com.wavemaker.commons.util.XMLWriter;
import com.wavemaker.commons.util.XmlDocument;

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
        PrintWriter pw = new PrintWriter(tempFile);
        XMLWriter xw = new XMLWriter(pw);
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

        XmlDocument<Customer> xmlDocument = JAXBUtils.unMarshall(jaxbContext, new FileInputStream(tempFile));
        Customer customer = xmlDocument.getObject();
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

    @Test
    public void testXmlWriter() throws IOException, JAXBException, ParserConfigurationException, SAXException {
        File tempFile = new File("build", "customer-data.xml");
        PrintWriter pw = new PrintWriter(tempFile);
        XMLWriter xw = new XMLWriter(pw);
        xw.setTextOnSameLineAsParentElement(true);
        xw.addVersion();
        //xw.addDoctype("Test","SYSTEM","file:///tmp/");
        xw.addElement("customer");
        xw.addNamespace("shortNs", "longNs");
        xw.setCurrentShortNS("shortNs");
        xw.unsetCurrentShortNS();

        Map<String, String> attributeMap = new HashMap<>();
        attributeMap.put("id", "100");
        xw.addAttribute(attributeMap);
        xw.addElement("name");
        xw.addText("Amanda", false);
        xw.closeElement();
        xw.addElement("age");
        xw.addText("20", false);
        xw.addComment("customer details");
        xw.closeElement();
        xw.finish();

        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        FileInputStream fileInputStream = new FileInputStream(tempFile);

        XmlDocument<Customer> xmlDocument = JAXBUtils.unMarshall(jaxbContext, fileInputStream);
        Customer customer = xmlDocument.getObject();
        assertEquals("Amanda\n  ", customer.getName());
        assertEquals(20, customer.getAge());

    }

}
