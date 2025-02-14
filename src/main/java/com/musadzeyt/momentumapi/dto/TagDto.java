package com.musadzeyt.momentumapi.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TagDto {
    private UUID id;
    private String description;
    private boolean completed;
    private List<UUID> activityIds;
}
