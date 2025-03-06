package com.musadzeyt.momentumapi.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.musadzeyt.momentumapi.enums.stat.TrendEnum;

import java.io.IOException;

public class TrendEnumSerializer extends StdSerializer<TrendEnum> {

    public TrendEnumSerializer() {
        super(TrendEnum.class);
    }

    @Override
    public void serialize(TrendEnum value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name().toLowerCase());
    }
}

