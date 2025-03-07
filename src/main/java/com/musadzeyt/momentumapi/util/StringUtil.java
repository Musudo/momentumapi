package com.musadzeyt.momentumapi.util;

public class StringUtil {
    /**
     * Converts the given input string to camelCase.
     * <p>
     * The conversion works by splitting the input string on whitespace and underscores,
     * converting the first word to lower case, and capitalizing the first letter of each subsequent word.
     * </p>
     *
     * @param input the input string to convert to camelCase.
     * @return the camelCase version of the input string.
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
