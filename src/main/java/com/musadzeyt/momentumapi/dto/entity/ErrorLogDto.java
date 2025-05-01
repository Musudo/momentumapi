package com.musadzeyt.momentumapi.dto.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class ErrorLogDto {
    private UUID id;
    private String message;
    private String entity;
    private UUID userId;
}
