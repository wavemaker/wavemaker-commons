/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.wavemaker.commons.util.JAXBUtils;
import com.wavemaker.commons.util.WMIOUtils;
import com.wavemaker.commons.util.XmlDocument;

import static org.testng.Assert.assertEquals;

/**
 * Created by Prithvi Medavaram on 13/10/15.
 */
public class JAXBUtilsTest {
    private JAXBContext getJaxbContext() throws JAXBException {
        return JAXBContext.newInstance(Customer.class);
    }

    @Test
    public void unMarshalInputStreamTest() throws JAXBException, ParserConfigurationException, SAXException, IOException {
        InputStream is = JAXBUtilsTest.class.getClassLoader().getResourceAsStream("Customer.xml");
        XmlDocument<Customer> xmlDocument = JAXBUtils.unMarshall(getJaxbContext(), is);
        Customer customer = xmlDocument.getObject();
        assertEquals("Prithvi", customer.getName());
        assertEquals(21, customer.getAge());
    }

    @Test
    public void unMarshalReaderTest() throws JAXBException, IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = null;

        try {
            inputStream = JAXBUtilsTest.class.getClassLoader().getResourceAsStream("Customer.xml");
            XmlDocument<Customer> xmlDocument = JAXBUtils.unMarshall(getJaxbContext(), inputStream);
            Customer customer = xmlDocument.getObject();
            assertEquals("Prithvi", customer.getName());
            assertEquals(21, customer.getAge());
        } finally {
            WMIOUtils.closeSilently(inputStream);
        }
    }

    @Test
    public void marshallTest() throws JAXBException, IOException, ParserConfigurationException, SAXException {
        Customer customer = new Customer();
        customer.setId(100);
        customer.setName("Uday");
        customer.setAge(24);

        OutputStream os = new ByteArrayOutputStream();
        JAXBUtils.marshall(getJaxbContext(), os, customer);
        String s = os.toString();

        InputStream is = new ByteArrayInputStream(s.getBytes());
        XmlDocument<Customer> xmlDocument = JAXBUtils.unMarshall(getJaxbContext(), is);
        Customer actualCustomer = xmlDocument.getObject();

        assertEquals(actualCustomer.getAge(), customer.getAge());
        assertEquals(actualCustomer.getName(), customer.getName());
        assertEquals(actualCustomer.getId(), 0);

    }
}
