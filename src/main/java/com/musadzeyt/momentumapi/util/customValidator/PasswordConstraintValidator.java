package com.musadzeyt.momentumapi.util.customValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * ConstraintValidator implementation for the {@link ValidPassword} annotation.
 * <p>
 * Validates that a given password string adheres to the defined password policy:
 * <ul>
 *   <li>At least 6 characters in length</li>
 *   <li>Contains at least one digit (0-9)</li>
 *   <li>Contains at least one uppercase letter (A-Z)</li>
 * </ul>
 * <p>
 * The policy is enforced using a regular expression stored in {@link #PWD_PATTERN}.
 * </p>
 *
 * @see ValidPassword
 */
public class PasswordConstraintValidator
        implements ConstraintValidator<ValidPassword, String> {

    /**
     * Regex pattern requiring at least one uppercase letter, one digit, and a minimum length of 6.
     */
    private static final Pattern PWD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{6,}$");

    /**
     * Validates the provided password against the configured {@link #PWD_PATTERN}.
     *
     * @param raw the raw password string to validate (maybe {@code null})
     * @param ctx the context in which the constraint is evaluated
     * @return {@code true} if {@code raw} is not null and matches the pattern; {@code false} otherwise
     */
    @Override
    public boolean isValid(String raw, ConstraintValidatorContext ctx) {
        return raw != null && PWD_PATTERN.matcher(raw).matches();
    }
}

