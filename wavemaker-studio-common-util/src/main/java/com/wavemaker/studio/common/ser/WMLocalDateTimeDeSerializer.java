package com.wavemaker.studio.common.ser;

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
import com.wavemaker.studio.common.WMRuntimeException;

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
