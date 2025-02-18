package com.musadzeyt.momentumapi.record;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestRecord(
        @NotBlank String username,
        @NotBlank String password
) {
}

