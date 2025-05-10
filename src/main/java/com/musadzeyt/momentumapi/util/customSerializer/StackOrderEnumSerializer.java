package com.musadzeyt.momentumapi.util.customSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.musadzeyt.momentumapi.enums.stat.StackOrderEnum;

import java.io.IOException;

public class StackOrderEnumSerializer extends StdSerializer<StackOrderEnum> {

    public StackOrderEnumSerializer() {
        super(StackOrderEnum.class);
    }

    @Override
    public void serialize(StackOrderEnum value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name().toLowerCase());
    }
}

