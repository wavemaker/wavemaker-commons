package com.wavemaker.studio.common.util.utils;

import com.wavemaker.studio.common.util.JAXBUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.URL;

/**
 * Created by Prithvi Medavaram on 13/10/15.
 */
public class JAXBUtilsTest {
    @Test
    public void unMarshalInputStreamTest() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        InputStream is = JAXBUtilsTest.class.getClassLoader().getResourceAsStream("Customer.xml");
        Customer customer = JAXBUtils.unMarshall(jaxbContext, is);
        assertEquals("Prithvi", customer.getName());
        assertEquals(21, customer.getAge());
    }

    @Test
    public void unMarshalReaderTest() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        URL url = JAXBUtilsTest.class.getClassLoader().getResource("Customer.xml");
        FileReader fileReader = new FileReader(url.getFile());
        Customer customer = JAXBUtils.unMarshall(jaxbContext, fileReader);
        assertEquals("Prithvi", customer.getName());
        assertEquals(21, customer.getAge());
    }

    @Test
    public void marshallTest() throws JAXBException, IOException {
        Customer customer = new Customer();
        customer.setId(100);
        customer.setName("Uday");
        customer.setAge(24);

        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);

        OutputStream os = new ByteArrayOutputStream();
        JAXBUtils.marshall(jaxbContext, os, customer);
        String s = os.toString();

        InputStream is = new ByteArrayInputStream(s.getBytes());
        Customer actualCustomer = JAXBUtils.unMarshall(jaxbContext, is);

        assertEquals(actualCustomer.getAge(), customer.getAge());
        assertEquals(actualCustomer.getName(), customer.getName());
        assertEquals(actualCustomer.getId(), 0);
    }
}
