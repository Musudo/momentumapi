package com.musadzeyt.momentumapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailDto {
    private UUID id;
    private String email;
    private UUID reviewId;
}
