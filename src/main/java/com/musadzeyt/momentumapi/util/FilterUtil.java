package com.musadzeyt.momentumapi.util;

import com.musadzeyt.momentumapi.dto.SearchCriteria;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;

public class FilterUtil {
    /**
     * Builds a Predicate based on the provided SearchCriteria.
     *
     * @param builder    The CriteriaBuilder used to create the Predicate.
     * @param expression The Expression corresponding to the entity attribute (or nested attribute).
     * @param criteria   The SearchCriteria containing the operation and value.
     * @return A Predicate representing the filtering condition.
     */
    public static Predicate buildPredicate(CriteriaBuilder builder, Expression<?> expression, SearchCriteria criteria) {
        String operation = criteria.getOperation();

        // Handle String comparison (like or equal)
        if (operation.equalsIgnoreCase(":")) {
            if (expression.getJavaType().equals(String.class)) {
                return builder.like(expression.as(String.class), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(expression, criteria.getValue());
            }
        }
        // Handle 'greater than' operation for Comparable types
        else if (operation.equalsIgnoreCase(">")) {
            return builder.greaterThan(expression.as(Comparable.class), (Comparable) criteria.getValue());
        }
        // Handle 'less than' operation for Comparable types
        else if (operation.equalsIgnoreCase("<")) {
            return builder.lessThan(expression.as(Comparable.class), (Comparable) criteria.getValue());
        }
        // Handle LocalDateTime comparison for equality
        else if (operation.equalsIgnoreCase("=")) {
            if (expression.getJavaType().equals(LocalDateTime.class)) {
                return builder.equal(expression.as(LocalDateTime.class), criteria.getValue());
            }
        }
        // Handle 'greater than' operation specifically for LocalDateTime
        else if (operation.equalsIgnoreCase(">=")) {
            if (expression.getJavaType().equals(LocalDateTime.class)) {
                return builder.greaterThanOrEqualTo(expression.as(LocalDateTime.class), (LocalDateTime) criteria.getValue());
            }
        }
        // Handle 'less than' operation specifically for LocalDateTime
        else if (operation.equalsIgnoreCase("<=")) {
            if (expression.getJavaType().equals(LocalDateTime.class)) {
                return builder.lessThanOrEqualTo(expression.as(LocalDateTime.class), (LocalDateTime) criteria.getValue());
            }
        }

        throw new UnsupportedOperationException("Operation " + operation + " is not supported.");
    }

    /**
     * Resolves a nested property path from the given root entity based on a dot-separated key.
     *
     * <p>This utility method extracts the {@link Path} corresponding to a given key.
     * If the key contains nested properties (indicated by the '.' character), it splits the key and iteratively
     * navigates through the nested attributes starting from the root. If the key does not contain any dots,
     * it returns the direct path to the property.
     *
     * <p>Example:
     * <pre>
     * // Given a key "user.email" and a Root<Activity> root, this method returns:
     * // root.get("user").get("email")
     * </pre>
     *
     * @param root the root from which to derive the path; represents the starting point of the entity graph.
     * @param key  the dot-separated string representing the property path (e.g., "user.email").
     * @return the {@link Path} corresponding to the property defined by the key.
     */
    public static Path<?> getPath(Root<?> root, String key) {
        Path<?> expression;
        if (key.contains(".")) {
            String[] keys = key.split("\\.");
            expression = root.get(keys[0]);
            for (int i = 1; i < keys.length; i++) {
                expression = expression.get(keys[i]);
            }
        } else {
            expression = root.get(key);
        }
        return expression;
    }
}
