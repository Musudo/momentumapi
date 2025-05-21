package com.musadzeyt.momentumapi.record;

import com.musadzeyt.momentumapi.util.customValidator.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestRecord(
        @NotBlank @Size(min = 2, max = 16, message = "First name should be 2 to 16 characters long") String firstName,
        @NotBlank @Size(min = 2, max = 16, message = "Last name should be 2 to 16 characters long") String lastName,
        @NotBlank @Email(message = "Email is invalid") String username,
        @NotBlank @ValidPassword String password
) {
}

