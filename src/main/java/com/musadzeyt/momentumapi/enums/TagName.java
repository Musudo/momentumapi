package com.musadzeyt.momentumapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TagName {
    PERSONAL,
    WORK,
    FINANCE,
    TRAINING,
    FAMILY,
    EDUCATION;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
