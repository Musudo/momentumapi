package com.musadzeyt.momentumapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// TODO: work out documentation more. In other classes as well

/**
 * Converts a {@code Set<String>} of roles into a JSON string.
 */
@Converter
public class RolesConverter implements AttributeConverter<Set<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return "[]"; // Return an empty JSON array if roles is empty
        }
        try {
            return objectMapper.writeValueAsString(roles);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting roles set to JSON", e);
        }
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new HashSet<>();
        }
        try {
            return objectMapper.readValue(
                    dbData,
                    objectMapper.getTypeFactory().constructCollectionType(HashSet.class, String.class)
            );
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to roles set", e);
        }
    }
}
