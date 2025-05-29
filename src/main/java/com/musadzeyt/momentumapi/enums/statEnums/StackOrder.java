package com.musadzeyt.momentumapi.enums.statEnums;

/**
 * {@code StackOrderEnum} defines the possible ordering strategies for collections or data stacks.
 *
 * <p>This enumeration is used to specify the order in which elements should be arranged.
 * The available ordering options include:
 * <ul>
 *   <li>{@code ASCENDING} - Represents an order where elements are arranged from the lowest to the highest.</li>
 *   <li>{@code DESCENDING} - Represents an order where elements are arranged from the highest to the lowest.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 *     StackOrderEnum order = StackOrderEnum.ASCENDING;
 *     if (order == StackOrderEnum.ASCENDING) {
 *         // Sort elements in ascending order.
 *     }
 * </pre>
 */
public enum StackOrder {
    /**
     * Represents an ascending order, where elements are arranged from the lowest to the highest.
     */
    ASCENDING,

    /**
     * Represents a descending order, where elements are arranged from the highest to the lowest.
     */
    DESCENDING,
}
