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
package com.wavemaker.commons.json.deserializer;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.wavemaker.commons.WMRuntimeException;

/**
 *
 * can deserialize date objects represented in one of the three formats "yyyy-MM-dd HH:mm:ss", "HH:mm:ss", ""yyyy-MM-dd".
 * @author Uday Shankar
 */
public class WMDateDeSerializer extends DateDeserializers.DateDeserializer {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final Logger logger = LoggerFactory.getLogger(WMDateDeSerializer.class);

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_STRING) {
            String value = jsonParser.getText();
            return getDate(value);
        }
        return super.deserialize(jsonParser, deserializationContext);
    }

    public static Date getDate(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            Date parsedDate = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).parse(value);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            logger.trace("{} is not in the expected date time format {}", value, DEFAULT_DATE_TIME_FORMAT);
        }
        try {
            Date parsedDate = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(value);
            return new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            logger.trace("{} is not in the expected date format {}", value, DEFAULT_DATE_FORMAT);
        }
        try {
            Date parsedDate = new SimpleDateFormat(DEFAULT_TIME_FORMAT).parse(value);
            return new Time(parsedDate.getTime());
        } catch (ParseException e) {
            logger.trace("{} is not in the expected time format {}", value, DEFAULT_TIME_FORMAT);
        }
        throw new WMRuntimeException("Failed to parse the string " + value + "as java.util.Date");
    }
}
