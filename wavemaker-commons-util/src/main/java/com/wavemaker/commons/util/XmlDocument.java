package com.wavemaker.commons.util;

import org.w3c.dom.Document;

/**
 * @author Uday Shankar
 */
public class XmlDocument<T> {
    private Document document;
    private T object;

    public XmlDocument(Document document, T object) {
        this.document = document;
        this.object = object;
    }

    public Document getDocument() {
        return document;
    }

    public T getObject() {
        return object;
    }
}