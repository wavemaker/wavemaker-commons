/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.testng.annotations.Test;

import com.wavemaker.commons.util.JAXBUtils;

import static org.testng.Assert.assertEquals;

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
