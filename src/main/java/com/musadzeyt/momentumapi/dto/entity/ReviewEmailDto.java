package com.musadzeyt.momentumapi.dto.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewEmailDto {
    private UUID id;
    private String email;
    private UUID reviewId;
    private String createdAt;
}
