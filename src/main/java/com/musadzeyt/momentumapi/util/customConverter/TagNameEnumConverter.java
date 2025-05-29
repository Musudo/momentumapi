package com.musadzeyt.momentumapi.util.customConverter;

import com.musadzeyt.momentumapi.enums.TagName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TagNameEnumConverter implements AttributeConverter<TagName, String> {
    @Override
    public String convertToDatabaseColumn(TagName attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public TagName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TagName.valueOf(dbData.toUpperCase());
    }
}
