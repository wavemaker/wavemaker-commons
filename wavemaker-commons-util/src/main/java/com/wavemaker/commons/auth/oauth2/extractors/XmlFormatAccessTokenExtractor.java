/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.auth.oauth2.extractors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wavemaker.commons.auth.oauth2.OAuth2Constants;
import com.wavemaker.commons.util.XMLUtils;

/**
 * Extracts access token when the response body type is application/xml
 * Used by Github if Accept-Type is set to application/xml in the request.
 *
 * Created by srujant on 24/8/17.
 */
public class XmlFormatAccessTokenExtractor extends MediaTypeBasedAccessTokenExtractor {

    private static final String OAUTH = "OAuth";
    private static final Logger logger = LoggerFactory.getLogger(XmlFormatAccessTokenExtractor.class);

    @Override
    public boolean canRead(MediaType mediaType) {
        return MediaType.APPLICATION_XML.equals(mediaType);
    }

    @Override
    protected String obtainAccessToken(AccessTokenRequestContext accessTokenRequestContext) {
        Document document = XMLUtils.getDocument(accessTokenRequestContext.getResponseBody());
        NodeList nodeList = document.getElementsByTagName(OAUTH);
        if (nodeList.getLength() == 0) {
            logger.warn("No oauth tag found");
            return null;
        }
        Element element = (Element) nodeList.item(0);

        NodeList accessTokenNodeList = element.getElementsByTagName(OAuth2Constants.ACCESS_TOKEN);
        if (accessTokenNodeList.getLength() == 0) {
            logger.warn("No accessToken tag found in response");
            return null;
        }
        return accessTokenNodeList.item(0).getTextContent();
    }

}
