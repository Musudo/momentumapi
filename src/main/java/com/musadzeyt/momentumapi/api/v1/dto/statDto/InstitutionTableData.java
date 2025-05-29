package com.musadzeyt.momentumapi.api.v1.dto.statDto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class InstitutionTableData {
    private UUID id;
    private String name;
    private String street;
    private String buildingNumber;
    private String postbox;
    private String city;
    private String postalCode;
    private String countryCode;
}
