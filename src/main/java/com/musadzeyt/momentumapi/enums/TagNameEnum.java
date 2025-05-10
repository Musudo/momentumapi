package com.musadzeyt.momentumapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration of predefined tag names used within the application to categorize
 * and organize entities or records (e.g., tasks, notes, or events).
 * <p>
 * Each enum constant holds a String value representing the tag's identifier.
 * </p>
 * <p>
 * Usage example:
 * <pre>{@code
 * TagNameEnum tag = TagNameEnum.PERSONAL;
 * String tagValue = tag.getName();  // "personal"
 * }</pre>
 *
 * @see #getName()
 */
@Getter
@AllArgsConstructor
public enum TagNameEnum {

    /**
     * Denotes a personal item or record not directly related to work.
     */
    PERSONAL("personal"),

    /**
     * Denotes a work-related item or record.
     */
    WORK("work"),

    /**
     * Denotes an item or record related to financial matters.
     */
    FINANCE("finance"),

    /**
     * Denotes an item or record related to training or learning activities.
     */
    TRAINING("training"),

    /**
     * Denotes an item or record related to family matters or relationships.
     */
    FAMILY("family"),

    /**
     * Denotes an item or record related to educational activities or studies.
     */
    EDUCATION("education");

    /**
     * The string identifier for this tag.
     */
    private final String name;
}