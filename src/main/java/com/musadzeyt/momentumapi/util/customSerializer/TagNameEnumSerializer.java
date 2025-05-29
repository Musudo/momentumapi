package com.musadzeyt.momentumapi.util.customSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.musadzeyt.momentumapi.enums.TagName;

import java.io.IOException;

public class TagNameEnumSerializer extends StdSerializer<TagName> {

    public TagNameEnumSerializer() {
        super(TagName.class);
    }

    @Override
    public void serialize(TagName value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name().toLowerCase());
    }
}

