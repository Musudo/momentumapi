package com.musadzeyt.momentumapi.enums.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@code ColumnNameEnum} defines the possible column names used within the system.
 *
 * <p>This enumeration is used to standardize the textual representation of column names for display
 * and data mapping purposes. The available column names include:
 * <ul>
 *   <li>{@code FIRST_NAME} - Represents the column for first names, labeled as "First Name".</li>
 *   <li>{@code LAST_NAME} - Represents the column for last names, labeled as "Last Name".</li>
 *   <li>{@code EMAIL} - Represents the column for email addresses, labeled as "Email".</li>
 *   <li>{@code PHONE} - Represents the column for phone numbers, labeled as "Phone".</li>
 *   <li>{@code JOB_TITLE} - Represents the column for job titles, labeled as "Job Title".</li>
 * </ul>
 *
 * <p>Each enum constant has an associated {@code String} value which can be retrieved using the
 * {@link #getColumnName()} method. This value is useful for display purposes or for mapping data to specific columns.
 *
 * <p>Example usage:
 * <pre>
 *     ColumnNameEnum column = ColumnNameEnum.FIRST_NAME;
 *     System.out.println("Column: " + column.getColumnName());
 * </pre>
 *
 * @see lombok.Getter
 * @see lombok.AllArgsConstructor
 */
@Getter
@AllArgsConstructor
public enum ColumnNameEnum {
    /**
     * Represents the column for first names.
     */
    FIRST_NAME("First Name"),

    /**
     * Represents the column for last names.
     */
    LAST_NAME("Last Name"),

    /**
     * Represents the column for email addresses.
     */
    EMAIL("Email"),

    /**
     * Represents the column for phone numbers.
     */
    PHONE("Phone"),

    /**
     * Represents the column for job titles.
     */
    JOB_TITLE("Job Title");

    /**
     * The string representation of the column name.
     */
    private final String columnName;
}
