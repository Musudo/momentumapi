package com.musadzeyt.momentumapi.util.customSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.musadzeyt.momentumapi.enums.statEnums.Trend;

import java.io.IOException;

public class TrendEnumSerializer extends StdSerializer<Trend> {

    public TrendEnumSerializer() {
        super(Trend.class);
    }

    @Override
    public void serialize(Trend value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name().toLowerCase());
    }
}

