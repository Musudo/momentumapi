package com.musadzeyt.momentumapi.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.musadzeyt.momentumapi.enums.TagNameEnum;

import java.io.IOException;

public class TagNameEnumSerializer extends StdSerializer<TagNameEnum> {

    public TagNameEnumSerializer() {
        super(TagNameEnum.class);
    }

    @Override
    public void serialize(TagNameEnum value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name().toLowerCase());
    }
}

