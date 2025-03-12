package com.musadzeyt.momentumapi.util;

import com.musadzeyt.momentumapi.enums.TagNameEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TagNameEnumConverter implements AttributeConverter<TagNameEnum, String> {

    @Override
    public String convertToDatabaseColumn(TagNameEnum tagNameEnum) {
        if (tagNameEnum == null) return null;
        return tagNameEnum.getName();
    }

    @Override
    public TagNameEnum convertToEntityAttribute(String tagName) {
        if (tagName == null) return null;
        return Stream.of(TagNameEnum.values())
                .filter(ate -> ate.getName().equals(tagName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
