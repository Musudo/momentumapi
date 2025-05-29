package com.musadzeyt.momentumapi.record;

import com.musadzeyt.momentumapi.util.customValidator.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @Schema(example = "John", description = "First name of the user")
        @NotBlank
        @Size(min = 2, max = 16, message = "First name should be 2 to 16 characters long")
        String firstName,
        @Schema(example = "Doe", description = "Last name of the user")
        @NotBlank
        @Size(min = 2, max = 16, message = "Last name should be 2 to 16 characters long")
        String lastName,
        @Schema(example = "guest@email.com", description = "The email of the user")
        @NotBlank
        @Email(message = "Email is invalid")
        String username,
        @Schema(example = "1Password", description = "The password of the user")
        @NotBlank
        @ValidPassword
        String password
) {
}

