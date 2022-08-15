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
package com.wavemaker.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * @author Uday Shankar
 */
public class JAXBUtils {

    private static final String JAXB_ENCODING = "UTF-8";

    private JAXBUtils() {
    }

    public static <T> XmlDocument<T> unMarshall(JAXBContext context, InputStream inputStream) throws JAXBException, IOException, SAXException, ParserConfigurationException {
        Unmarshaller unmarshaller = null;
        try {
            Document document = XMLUtils.readDocument(inputStream);
            unmarshaller = context.createUnmarshaller();
            T t = (T) unmarshaller.unmarshal(document);
            return new XmlDocument<>(document, t);
        } finally {
            closeResources(inputStream, unmarshaller);
        }
    }

    public static void marshall(JAXBContext context, OutputStream outputStream, Object object) throws JAXBException {
        Marshaller marshaller = null;
        try {
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, JAXB_ENCODING);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, outputStream);
        } finally {
            closeResources(outputStream, null);
        }
    }

    public static JAXBContext getJAXBContext(String packagePrefix) throws JAXBException {
        return JAXBContext.newInstance(packagePrefix);
    }

    private static void closeResources(AutoCloseable resourceStream, Unmarshaller unmarshaller) {
        WMIOUtils.closeByLogging(resourceStream);
        //Doing it on implementation check as the specific needs to be closed to prevent thread local memory leaks
        if (unmarshaller != null && unmarshaller instanceof AutoCloseable) {
            WMIOUtils.closeByLogging((AutoCloseable) unmarshaller);
        }
    }
}
