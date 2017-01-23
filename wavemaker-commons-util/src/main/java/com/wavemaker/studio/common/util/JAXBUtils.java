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
package com.wavemaker.studio.common.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.sun.xml.bind.v2.runtime.MarshallerImpl;
import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
import com.wavemaker.studio.common.WMRuntimeException;

/**
 * @author Uday Shankar
 */
public class JAXBUtils {

    private final static String JAXB_ENCODING = "UTF-8";

    public static <T> T unMarshall(JAXBContext context, InputStream inputStream) throws JAXBException {
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(inputStream);
        } finally {
            closeResources(inputStream, unmarshaller);
        }
    }

    public static <T> T unMarshall(JAXBContext context, Reader reader) throws JAXBException {
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(reader);
        } finally {
            closeResources(reader, unmarshaller);
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
        IOUtils.closeByLogging(resourceStream);
        //Doing it on implementation check as the specific needs to be closed to prevent thread local memory leaks
        if (unmarshaller != null && unmarshaller instanceof UnmarshallerImpl) {
            IOUtils.closeByLogging((UnmarshallerImpl) unmarshaller);
        }
    }
}
