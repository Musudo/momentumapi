package com.musadzeyt.momentumapi.dto;

import com.musadzeyt.momentumapi.domain.User;
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
    private User user;
}
