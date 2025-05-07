package com.musadzeyt.momentumapi.dto.entityDto;

import lombok.Data;

import java.util.UUID;

@Data
public class InstitutionDto {
    private UUID id;
    private String name;
    private String street;
    private String buildingNumber;
    private String postbox;
    private String city;
    private String postalCode;
    private String countryCode;
    private UserDto user;
    private String createdAt;
}
