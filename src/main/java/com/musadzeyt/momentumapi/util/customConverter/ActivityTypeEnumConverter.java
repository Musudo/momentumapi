package com.musadzeyt.momentumapi.util.customConverter;

import com.musadzeyt.momentumapi.enums.ActivityType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ActivityTypeEnumConverter implements AttributeConverter<ActivityType, String> {

    @Override
    public String convertToDatabaseColumn(ActivityType activityType) {
        if (activityType == null) return null;
        return activityType.getType();
    }

    @Override
    public ActivityType convertToEntityAttribute(String activityType) {
        if (activityType == null) return null;
        return Stream.of(ActivityType.values())
                .filter(ate -> ate.getType().equals(activityType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
