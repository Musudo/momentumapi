package com.musadzeyt.momentumapi.api.v1.dto.entityDto;

import lombok.Data;

import java.util.UUID;

@Data
public class ExternalParticipantDto {
    private UUID id;
    private String name;
    private String email;
    private String createdAt;
}
