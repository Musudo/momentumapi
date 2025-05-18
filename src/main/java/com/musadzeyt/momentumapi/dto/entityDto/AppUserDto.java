package com.musadzeyt.momentumapi.dto.entityDto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class AppUserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
    private String createdAt;
}
