package com.musadzeyt.momentumapi.record;

import com.musadzeyt.momentumapi.util.customValidator.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestRecord(
        @NotBlank @Email(message = "Email is invalid") String username,
        @NotBlank @ValidPassword String password
) {
}

