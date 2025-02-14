package com.musadzeyt.momentumapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequestDto {
    private String firstName;
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

