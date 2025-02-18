package com.musadzeyt.momentumapi.record;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationRequestRecord(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String username,
        @NotBlank String password
) {
}

