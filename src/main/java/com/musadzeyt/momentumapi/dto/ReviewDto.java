package com.musadzeyt.momentumapi.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ReviewDto {
    private UUID id;
    private String title;
    private String content;
    private UUID userId;
    private UUID activityId;
    private List<UUID> attachmentIds;
    private List<UUID> emailIds;
}
