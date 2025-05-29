package com.musadzeyt.momentumapi.api.v1.dto.entityDto;

import lombok.Data;

import java.util.UUID;

@Data
public class ErrorLogDto {
    private UUID id;
    private String message;
    private String originalClass;
    private String originalMethod;
    private int originalLineNumber;
    private UUID userId;
}
