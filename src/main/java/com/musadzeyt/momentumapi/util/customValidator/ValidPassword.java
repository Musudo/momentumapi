package com.musadzeyt.momentumapi.util.customValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom constraint annotation to validate password strength according to the following rules:
 * <ul>
 *   <li>At least 6 characters in length</li>
 *   <li>Contains at least one digit (0-9)</li>
 *   <li>Contains at least one uppercase letter (A-Z)</li>
 * </ul>
 * <p>
 * This annotation can be placed on fields, method parameters, and record components to enforce
 * the defined password policy at runtime via the Jakarta Bean Validation framework.
 * <p>
 * Example usage:
 * <pre>{@code
 * public record UserDto(
 *     @ValidPassword String password
 * ) { }
 * }</pre>
 *
 * @see PasswordConstraintValidator
 */
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    /**
     * The default validation failure message if the password does not satisfy the policy.
     *
     * @return the message template
     */
    String message() default
            "Password must be at least 6 characters, contain at least one digit and one uppercase letter";

    /**
     * Allows the specification of validation groups, to selectively apply this constraint
     * in different contexts (e.g. {@code OnCreate}, {@code OnUpdate}).
     *
     * @return the validation groups
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients of the Jakarta Bean Validation API to assign custom payload
     * objects to a constraint. This attribute is not used by the API itself.
     *
     * @return the payload classes
     */
    Class<? extends Payload>[] payload() default {};
}


