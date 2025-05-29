package com.musadzeyt.momentumapi.api.v1.dto.entityDto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewEmailDto {
    private UUID id;
    private String email;
    private UUID reviewId;
    private String createdAt;
}
