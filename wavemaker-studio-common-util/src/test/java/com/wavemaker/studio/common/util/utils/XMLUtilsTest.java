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
