package com.musadzeyt.momentumapi.enums.stat;

/**
 * {@code TrendEnum} defines the possible trends that can be observed within the system.
 *
 * <p>This enumeration is used to indicate the direction of change or movement in various contexts.
 * The available trend options include:
 * <ul>
 *   <li>{@code UP} - Indicates an upward trend or positive change.</li>
 *   <li>{@code DOWN} - Indicates a downward trend or negative change.</li>
 *   <li>{@code NEUTRAL} - Indicates a neutral state with no significant change.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 *     TrendEnum trend = TrendEnum.UP;
 *     if (trend == TrendEnum.UP) {
 *         // Execute logic for upward trend.
 *     }
 * </pre>
 */
public enum TrendEnum {
    /**
     * Indicates an upward trend or positive change.
     */
    UP,

    /**
     * Indicates a downward trend or negative change.
     */
    DOWN,

    /**
     * Indicates a neutral state with no significant change.
     */
    NEUTRAL;
}
