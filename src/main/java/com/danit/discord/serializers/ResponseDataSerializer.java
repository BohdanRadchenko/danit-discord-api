package com.danit.discord.serializers;

import com.danit.discord.dto.ResponseData;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

public class ResponseDataSerializer extends StdSerializer<ResponseData> {

    private record Errors(List<String> errors) {
    }

    public ResponseDataSerializer() {
        this(null);
    }

    public ResponseDataSerializer(Class<ResponseData> t) {
        super(t);
    }

    @Override
    public void serialize(
            ResponseData value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeBooleanField("success", value.isSuccess());
        if (value.getMessage() != null) {
            jgen.writeStringField("message", value.getMessage());
        }
        if (value.getData() != null) {
            jgen.writeObjectField("data", value.getData());
        }
        if (value.getErrors() != null && value.getErrors().size() > 0) {
            jgen.writeFieldName("errors");
            jgen.writeObject(value.getErrors());
        }
        jgen.writeEndObject();
    }
}
