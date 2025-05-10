package com.musadzeyt.momentumapi.util;

/**
 * Utility class for String transformations.
 * <p>
 * Provides common methods to manipulate and convert String formats.
 */
public class StringUtil {

    /**
     * Converts the given input string to camelCase.
     * <p>
     * Splits the input on whitespace or underscores, then:
     * <ul>
     *   <li>Leaves the first segment entirely in lowercase.</li>
     *   <li>Capitalizes the first character of each subsequent segment,
     *       followed by lowercase for the remainder.</li>
     * </ul>
     * <p>
     * Examples:
     * <ul>
     *   <li>"hello world" → "helloWorld"</li>
     *   <li>"JAVA_UTIL" → "javaUtil"</li>
     *   <li>"Mixed_Case Input" → "mixedCaseInput"</li>
     * </ul>
     *
     * @param input the original string, may be null or empty
     * @return the camelCase representation, or the original input if null/empty
     */
    public static String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Split input string by spaces or underscores
        String[] parts = input.split("[\\s_]+");
        StringBuilder camelCaseString = new StringBuilder();

        // Append the first word in lowercase
        camelCaseString.append(parts[0].toLowerCase());

        // Process remaining words: capitalize first letter and append the rest in lowercase
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                camelCaseString.append(Character.toUpperCase(parts[i].charAt(0)));
                if (parts[i].length() > 1) {
                    camelCaseString.append(parts[i].substring(1).toLowerCase());
                }
            }
        }

        return camelCaseString.toString();
    }
}