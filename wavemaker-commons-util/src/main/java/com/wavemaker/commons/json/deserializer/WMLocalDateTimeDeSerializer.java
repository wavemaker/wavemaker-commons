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
package com.wavemaker.commons.json.deserializer;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.wavemaker.commons.WMRuntimeException;

/**
 * @author Uday Shankar
 */
public class WMLocalDateTimeDeSerializer extends JsonDeserializer<LocalDateTime> {

    private final static DateTimeFormatter parser = ISODateTimeFormat.localDateOptionalTimeParser();

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_STRING) {
            String value = jsonParser.getText();
            return getLocalDateTime(value);
        }
        throw new WMRuntimeException("Not a String value");
    }

    public static LocalDateTime getLocalDateTime(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return parser.parseLocalDateTime(value);
    }
}
