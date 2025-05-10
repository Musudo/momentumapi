package com.musadzeyt.momentumapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA {@link AttributeConverter} to convert a {@link Set} of role strings
 * to a JSON array representation for storage in a single database column,
 * and vice versa.
 * <p>
 * Uses Jackson's {@link ObjectMapper} to serialize and deserialize the JSON.
 * When converting to the database column, null or empty sets become an empty JSON array ("[]").
 * When converting to the entity attribute, null or empty strings become an empty {@link HashSet}.
 * <p>
 * Example usage in an entity:
 * <pre>{@code
 * @Convert(converter = RolesConverter.class)
 * private Set<String> roles;
 * }</pre>
 *
 * @see AttributeConverter
 * @see ObjectMapper
 */
@Converter
public class RolesConverter implements AttributeConverter<Set<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts the given set of roles to its JSON string representation.
     *
     * @param roles the set of role names (may be null or empty)
     * @return a JSON array string (e.g., "[\"ADMIN\",\"USER\"]"),
     * or "[]" if the input set is null or empty
     * @throws IllegalArgumentException if JSON serialization fails
     */
    @Override
    public String convertToDatabaseColumn(Set<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(roles);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting roles set to JSON", e);
        }
    }

    /**
     * Converts the JSON string from the database column back into a {@link Set} of roles.
     *
     * @param dbData the JSON array string (may be null or empty)
     * @return a {@link Set} of role names, or an empty set if input is null or empty
     * @throws IllegalArgumentException if JSON deserialization fails
     */
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
