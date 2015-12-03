/**
 * Copyright © 2015 WaveMaker, Inc.
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
package com.wavemaker.studio.common.util.utils;

import static org.testng.Assert.*;
import com.wavemaker.studio.common.util.XMLUtils;
import org.testng.annotations.Test;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Saraswathi Rekhala on 19/10/15.
 */
public class XMLUtilsTest {

    @Test
    public void escapeTest() {
        assertEquals(XMLUtils.escape("\"sample\""), "&quot;sample&quot;");
        assertEquals(XMLUtils.escape("<sample>"), "&lt;sample&gt;");
        assertEquals(XMLUtils.escape("&sample"), "&amp;sample");
        assertEquals(XMLUtils.escape("\'sample\'"), "&apos;sample&apos;");
    }

    @Test
    public void attributesToMapReaderTest() throws FileNotFoundException, XMLStreamException {
        URL resource = XMLUtilsTest.class.getClassLoader().getResource("Customer.xml");
        String filePath = resource.getFile();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(filePath));
        reader.next();
        Map<String, String> output = XMLUtils.attributesToMap(reader);
        assertEquals(output.size(), 1);
        Set<Map.Entry<String, String>> entries = output.entrySet();
        Map.Entry<String, String> entry = entries.iterator().next();
        assertEquals(entry.getKey(), "id");
        assertEquals(entry.getValue(), "100");
    }

    @Test
    public void attributesToMapScopeTest() throws IOException,XMLStreamException {
        URL resource = XMLUtilsTest.class.getClassLoader().getResource("Customer.xml");
        String filePath = resource.getFile();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(filePath));
        reader.next();
        Map<String, String> output = XMLUtils.attributesToMap("xyz",reader);
        assertEquals(output.size(), 1);
        Set<Map.Entry<String, String>> entries = output.entrySet();
        Map.Entry<String, String> entry = entries.iterator().next();
        assertEquals(entry.getKey(), "xyz.id");
        assertEquals(entry.getValue(), "100");

    }

}
