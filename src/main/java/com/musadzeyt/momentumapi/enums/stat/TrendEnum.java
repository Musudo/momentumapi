package com.musadzeyt.momentumapi.enums.stat;

/**
 * Enum representing the possible trends for a statistic.
 *
 * <p>This enum is used to indicate the direction of a trend in data,
 * providing a type-safe way to represent the following choices:
 * <ul>
 *   <li><b>{@code UP}</b> - Indicates an increasing or upward trend.</li>
 *   <li><b>{@code DOWN}</b> - Indicates a decreasing or downward trend.</li>
 *   <li><b>{@code NEUTRAL}</b> - Indicates no significant change or a balanced trend.</li>
 * </ul>
 * </p>
 */
public enum TrendEnum {
    UP,
    DOWN,
    NEUTRAL;
}
