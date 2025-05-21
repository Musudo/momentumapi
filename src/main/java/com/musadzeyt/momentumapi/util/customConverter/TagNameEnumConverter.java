package com.musadzeyt.momentumapi.util.customConverter;

import com.musadzeyt.momentumapi.enums.TagNameEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TagNameEnumConverter implements AttributeConverter<TagNameEnum, String> {
    @Override
    public String convertToDatabaseColumn(TagNameEnum attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public TagNameEnum convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TagNameEnum.valueOf(dbData.toUpperCase());
    }
}
