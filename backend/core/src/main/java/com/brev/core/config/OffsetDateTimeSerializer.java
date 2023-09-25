package com.brev.core.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetDateTime;

public class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {


    @Override
    public void serialize(OffsetDateTime dateTime, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(dateTime.toInstant().toString());
    }

    @Override
    public Class<OffsetDateTime> handledType() {
        return OffsetDateTime.class;
    }
}

