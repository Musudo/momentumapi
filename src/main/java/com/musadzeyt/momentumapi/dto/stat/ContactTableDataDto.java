package com.musadzeyt.momentumapi.dto.stat;

import com.musadzeyt.momentumapi.dto.InstitutionDto;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class ContactTableDataDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String jobTitle;
    private String institutionName;
}
