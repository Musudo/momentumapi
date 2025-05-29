package com.musadzeyt.momentumapi.util.customSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.musadzeyt.momentumapi.enums.statEnums.StackOrder;

import java.io.IOException;

public class StackOrderEnumSerializer extends StdSerializer<StackOrder> {

    public StackOrderEnumSerializer() {
        super(StackOrder.class);
    }

    @Override
    public void serialize(StackOrder value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name().toLowerCase());
    }
}

