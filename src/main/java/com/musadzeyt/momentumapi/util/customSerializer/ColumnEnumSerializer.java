package com.musadzeyt.momentumapi.util.customSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.musadzeyt.momentumapi.enums.statEnums.ColumnName;

import java.io.IOException;

public class ColumnEnumSerializer extends StdSerializer<ColumnName> {

    public ColumnEnumSerializer() {
        super(ColumnName.class);
    }

    @Override
    public void serialize(ColumnName value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String name = value.name();
        String[] parts = name.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (!part.isEmpty()) {
                // Capitalize first letter and make rest lower case
                sb.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1).toLowerCase());
                if (i < parts.length - 1) {
                    sb.append(" ");
                }
            }
        }
        gen.writeString(sb.toString());
    }
}

