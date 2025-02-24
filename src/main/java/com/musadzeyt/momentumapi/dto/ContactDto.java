package com.musadzeyt.momentumapi.dto;

import com.musadzeyt.momentumapi.domain.Institution;
import lombok.Data;

import java.util.UUID;

@Data
public class ContactDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email1;
    private String email2;
    private String phone1;
    private String phone2;
    private String jobTitle;
    private UUID userId;
    private UUID institutionId;
    private InstitutionDto institution;
}
