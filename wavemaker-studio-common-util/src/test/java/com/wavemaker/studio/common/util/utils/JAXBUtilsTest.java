package com.wavemaker.studio.common.util.utils;

import com.wavemaker.studio.common.util.JAXBUtils;
import org.testng.annotations.DataProvider;
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
    private JAXBContext getJaxbContext() throws JAXBException {
        return JAXBContext.newInstance(Customer.class);
    }

    @Test
    public void unMarshalInputStreamTest() throws JAXBException {
        InputStream is = JAXBUtilsTest.class.getClassLoader().getResourceAsStream("Customer.xml");
        Customer customer = JAXBUtils.unMarshall(getJaxbContext(), is);
        assertEquals("Prithvi", customer.getName());
        assertEquals(21, customer.getAge());
    }

    @Test
    public void unMarshalReaderTest() throws JAXBException, IOException {
        URL url = JAXBUtilsTest.class.getClassLoader().getResource("Customer.xml");
        FileReader fileReader = new FileReader(url.getFile());
        Customer customer = JAXBUtils.unMarshall(getJaxbContext(), fileReader);
        assertEquals("Prithvi", customer.getName());
        assertEquals(21, customer.getAge());
    }

    @Test
    public void marshallTest() throws JAXBException, IOException {
        Customer customer = new Customer();
        customer.setId(100);
        customer.setName("Uday");
        customer.setAge(24);

        OutputStream os = new ByteArrayOutputStream();
        JAXBUtils.marshall(getJaxbContext(), os, customer);
        String s = os.toString();

        InputStream is = new ByteArrayInputStream(s.getBytes());
        Customer actualCustomer = JAXBUtils.unMarshall(getJaxbContext(), is);

        assertEquals(actualCustomer.getAge(), customer.getAge());
        assertEquals(actualCustomer.getName(), customer.getName());
        assertEquals(actualCustomer.getId(), 0);

    }
}
