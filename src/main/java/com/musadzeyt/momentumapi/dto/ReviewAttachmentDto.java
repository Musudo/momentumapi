package com.musadzeyt.momentumapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewAttachmentDto {
    private UUID id;
    private String path;
    private UUID reviewId;
}
