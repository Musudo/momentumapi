package com.musadzeyt.momentumapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagNameEnum {
    WORKSHOP("workshop"),
    TRAINING("training"),
    FINANCE("finance"),
    SALE("sale"),
    NETWORKING("networking"),
    STAKEHOLDER("stakeholder");

    private final String name;
}
