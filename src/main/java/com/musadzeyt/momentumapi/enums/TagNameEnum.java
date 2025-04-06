package com.musadzeyt.momentumapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagNameEnum {
    PERSONAL("personal"),
    WORK("work"),
    FINANCE("finance"),
    TRAINING("training"),
    FAMILY("family"),
    EDUCATION("education");

    private final String name;
}
