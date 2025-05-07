package com.musadzeyt.momentumapi.dto.entityDto;

import lombok.Data;

import java.util.UUID;

@Data
public class VoiceMemoDto {
    private UUID id;
    private String path;
    private boolean completed;
    private UUID userId;
}
