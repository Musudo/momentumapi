package com.musadzeyt.momentumapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@code ActivityTypeEnum} enumerates the various modes of activities available in the system.
 *
 * <p>This enumeration is used to categorize activities based on how they are conducted.
 * The available activity types include:
 * <ul>
 *   <li>{@code ONLINE} - Activities conducted via the internet.</li>
 *   <li>{@code PHONE} - Activities conducted via telephone.</li>
 *   <li>{@code PHYSICAL} - In-person or physical activities.</li>
 * </ul>
 *
 * <p>Each enum constant has an associated {@code String} value which can be retrieved using the
 * {@link #getType()} method. This value is useful for display purposes or logic that depends on the
 * textual representation of the activity type.
 *
 * <p>Example usage:
 * <pre>
 *     ActivityTypeEnum activityType = ActivityTypeEnum.ONLINE;
 *     System.out.println("Selected Activity Type: " + activityType.getType());
 * </pre>
 *
 * @see lombok.Getter
 * @see lombok.AllArgsConstructor
 */
@Getter
@AllArgsConstructor
public enum ActivityType {
    /**
     * Represents activities conducted via the internet.
     */
    ONLINE("online"),

    /**
     * Represents activities conducted via telephone.
     */
    PHONE("phone"),

    /**
     * Represents in-person or physical activities.
     */
    PHYSICAL("physical");

    /**
     * The string representation of the activity type.
     */
    private final String type;
}
