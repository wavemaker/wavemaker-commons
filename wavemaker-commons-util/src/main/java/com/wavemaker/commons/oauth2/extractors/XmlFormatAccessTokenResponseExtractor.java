package com.wavemaker.commons.oauth2.extractors;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.MediaType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.oauth2.OAuth2Constants;

/**
 * Extracts access token when the response body type is application/xml
 * Used by Github if Accept-Type is set to application/xml in the request.
 *
 * Created by srujant on 24/8/17.
 */
public class XmlFormatAccessTokenResponseExtractor extends MediaTypeBasedAccessTokenExtractor {

    private static final String OAUTH = "OAuth";

    @Override
    public boolean canRead(MediaType mediaType) {
        return MediaType.APPLICATION_XML.equals(mediaType.getType() + "/" + mediaType.getSubtype());
    }

    @Override
    protected String obtainAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(accessTokenRequestContext.getResponseBody());
            NodeList nodeList = document.getElementsByTagName(OAUTH);
            Element element = (Element) nodeList.item(0);
            return element.getElementsByTagName(OAuth2Constants.ACCESS_TOKEN).item(0).getTextContent();

        } catch (ParserConfigurationException e) {
            throw new WMRuntimeException(e);
        } catch (SAXException e) {
            throw new WMRuntimeException(e);
        } catch (IOException e) {
            throw new WMRuntimeException(e);
        }

    }

}
