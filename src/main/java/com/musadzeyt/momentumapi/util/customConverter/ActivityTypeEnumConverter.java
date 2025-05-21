package com.musadzeyt.momentumapi.util.customConverter;

import com.musadzeyt.momentumapi.enums.ActivityTypeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ActivityTypeEnumConverter implements AttributeConverter<ActivityTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(ActivityTypeEnum activityTypeEnum) {
        if (activityTypeEnum == null) return null;
        return activityTypeEnum.getType();
    }

    @Override
    public ActivityTypeEnum convertToEntityAttribute(String activityType) {
        if (activityType == null) return null;
        return Stream.of(ActivityTypeEnum.values())
                .filter(ate -> ate.getType().equals(activityType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
