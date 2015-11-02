package com.wavemaker.studio.common.util.utils;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;

import org.testng.annotations.Test;

import com.wavemaker.studio.common.util.JAXBUtils;
import com.wavemaker.studio.common.util.XMLWriter;

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
