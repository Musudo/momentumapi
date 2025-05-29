package com.musadzeyt.momentumapi.api.v1.dto.entityDto;

import lombok.Data;

import java.util.UUID;

@Data
public class VoiceMemoDto {
    private UUID id;
    private String path;
    private boolean completed;
    private UUID userId;
}
