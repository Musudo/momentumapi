package com.musadzeyt.momentumapi.util;

import com.musadzeyt.momentumapi.dto.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;

/**
 * Utility class for constructing JPA Criteria API predicates and navigating entity paths
 * based on dynamic search criteria.
 * <p>
 * Provides methods to build a {@link Predicate} from a given {@link SearchCriteria} and
 * to resolve nested property paths from a {@link Root}.
 */
public class FilterUtil {

    /**
     * Builds a JPA {@link Predicate} for the provided expression and criteria.
     * <p>
     * Supported operations:
     * <ul>
     *   <li>":" – String "like" match or equality for non-String types</li>
     *   <li>">", "<" – greater-than and less-than for {@link Comparable} types</li>
     *   <li>"=" – equality for {@link LocalDateTime} fields</li>
     *   <li>">=", "<=" – inclusive comparisons for {@link LocalDateTime}</li>
     * </ul>
     *
     * @param builder    the {@link CriteriaBuilder} used to construct predicates
     * @param expression the field expression to compare
     * @param criteria   the {@link SearchCriteria} containing operation and value
     * @return a {@link Predicate} representing the criteria
     * @throws UnsupportedOperationException if the operation is not recognized or not supported for the expression type
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Predicate buildPredicate(
            CriteriaBuilder builder,
            Expression<?> expression,
            SearchCriteria criteria) {
        String operation = criteria.getOperation();

        if (operation.equalsIgnoreCase(":")) {
            if (expression.getJavaType().equals(String.class)) {
                // Partial match for strings
                return builder.like(
                        expression.as(String.class),
                        "%" + criteria.getValue() + "%"
                );
            } else {
                // Exact match for other types
                return builder.equal(expression, criteria.getValue());
            }
        } else if (operation.equalsIgnoreCase(">")) {
            // Greater-than for comparable types
            return builder.greaterThan(
                    (Expression<? extends Comparable>) expression,
                    (Comparable) criteria.getValue()
            );
        } else if (operation.equalsIgnoreCase("<")) {
            // Less-than for comparable types
            return builder.lessThan(
                    (Expression<? extends Comparable>) expression,
                    (Comparable) criteria.getValue()
            );
        } else if (operation.equalsIgnoreCase("=")) {
            if (expression.getJavaType().equals(LocalDateTime.class)) {
                // Exact match for LocalDateTime
                return builder.equal(
                        expression.as(LocalDateTime.class),
                        criteria.getValue()
                );
            }
        } else if (operation.equalsIgnoreCase(">=")) {
            if (expression.getJavaType().equals(LocalDateTime.class)) {
                // Inclusive greater-than for LocalDateTime
                return builder.greaterThanOrEqualTo(
                        expression.as(LocalDateTime.class),
                        (LocalDateTime) criteria.getValue()
                );
            }
        } else if (operation.equalsIgnoreCase("<=")) {
            if (expression.getJavaType().equals(LocalDateTime.class)) {
                // Inclusive less-than for LocalDateTime
                return builder.lessThanOrEqualTo(
                        expression.as(LocalDateTime.class),
                        (LocalDateTime) criteria.getValue()
                );
            }
        }

        throw new UnsupportedOperationException(
                "Operation " + operation + " is not supported."
        );
    }

    /**
     * Resolves a nested property path from the given {@link Root} based on a dot-separated key.
     * <p>
     * For example, key "address.city" will navigate root.get("address").get("city").
     *
     * @param root the root entity in the criteria query
     * @param key  the dot-separated property path
     * @return a {@link Path} representing the nested attribute
     */
    public static Path<?> getPath(Root<?> root, String key) {
        Path<?> path;
        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            path = root.get(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                path = path.get(parts[i]);
            }
        } else {
            path = root.get(key);
        }
        return path;
    }
}
