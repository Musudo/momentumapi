package com.musadzeyt.momentumapi.record;

import com.musadzeyt.momentumapi.util.customValidator.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
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

