package com.musadzeyt.momentumapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskDto {
    private UUID id;
    private String description;
    private boolean completed;
    private UUID userId;
    private UUID activityId;
}
