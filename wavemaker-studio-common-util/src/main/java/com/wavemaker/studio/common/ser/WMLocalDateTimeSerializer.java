package com.wavemaker.studio.common.ser;

import java.io.IOException;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Uday Shankar
 */
public class WMLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private final static DateTimeFormatter format = ISODateTimeFormat.dateTime();

    @Override
    public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(format.print(value));
    }
}
