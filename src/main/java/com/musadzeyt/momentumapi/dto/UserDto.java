package com.musadzeyt.momentumapi.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
    private String createdAt;
}
