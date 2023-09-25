package com.brev.core.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;

public class InstantSerializer extends JsonSerializer<Instant> {


    @Override
    public void serialize(Instant dateTime, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(dateTime.toString());
    }

    @Override
    public Class<Instant> handledType() {
        return Instant.class;
    }
}
