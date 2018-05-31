/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
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
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.io.File;

/**
 * @author Simon Toens
 */
public abstract class XMLUtils {

    public static final String SCOPE_SEP = ".";

    private XMLUtils() {
    }

    public static String escape(String s) {
        return StringEscapeUtils.escapeXml(s);
    }

    public static XMLWriter newXMLWriter(PrintWriter pw) {
        XMLWriter rtn = new XMLWriter(pw, 4 /* indent */, 4 /* attrs on line */);
        rtn.setTextOnSameLineAsParentElement(true);
        return rtn;
    }

    public static Map<String, String> attributesToMap(XMLStreamReader reader) {
        return attributesToMap("", reader);
    }

    public static Map<String, String> attributesToMap(String scope, XMLStreamReader reader) {
        int numAttrs = reader.getAttributeCount();
        if (numAttrs == 0) {
            return Collections.emptyMap();
        }
        Map<String, String> rtn = new HashMap<>(numAttrs);
        for (int i = 0; i < numAttrs; i++) {
            StringBuilder attrName = new StringBuilder();
            if (scope.length() > 0) {
                attrName.append(scope).append(SCOPE_SEP);
            }
            attrName.append(reader.getAttributeName(i).toString());
            rtn.put(attrName.toString(), reader.getAttributeValue(i));
        }
        return rtn;
    }

    public static Document getDocument(File file) {
        return getDocument(file.getContent().asInputStream());
    }

    public static Document getNewDocument() {
        return getDocumentBuilder().newDocument();
    }


    public static Document getDocument(String input) {
        return getDocument(IOUtils.toInputStream(input, Charset.forName("UTF-8")));
    }

    public static Document getDocument(InputStream inputStream) {
        try {
            DocumentBuilder documentBuilder = getDocumentBuilder();
            return documentBuilder.parse(inputStream);
        } catch (SAXException | IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.xml.parse.error"), e);
        } finally {
            WMIOUtils.closeSilently(inputStream);
        }
    }


    public static void putSimpleTextElement(Document document, Element parentElement, String key, String value) {
        NodeList childNodes = parentElement.getChildNodes();
        boolean hasElement = false;
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) childNodes.item(i);
                if (key.equals(element.getTagName())) {
                    element.setTextContent(value);
                    hasElement = true;
                }
            }
        }
        if (!hasElement) {
            Element element = document.createElement(key);
            element.setTextContent(value);
            parentElement.appendChild(element);
        }
    }

    public static List<Element> getFirstLevelElementsByTagName(Element element, String name) {
        NodeList allChilds = element.getElementsByTagName(name);
        List<Element> childElements = new ArrayList<>();
        
        for (int i = 0; i < allChilds.getLength(); i++) {
            Node child = allChilds.item(i);
            if (Objects.equals(child.getParentNode(), element)) {
                childElements.add((Element) child);
            }
        }
        return childElements;
    }

    public static void updateDocument(Document document, File file) {
        updateDocument(document, file.getContent().asOutputStream());
    }


    public static void updateDocument(Document document, OutputStream outputStream) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Source source = new DOMSource(document);
            Result dest = new StreamResult(outputStream);
            transformer.transform(source, dest);
        } catch (TransformerException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.write.document"), e);
        } finally {
            WMIOUtils.closeSilently(outputStream);
        }
    }


    private static DocumentBuilder getDocumentBuilder() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            return documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.fetch.documentbuilder"), e);
        }
    }

}
