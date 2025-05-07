package com.musadzeyt.momentumapi.dto.entityDto;

import lombok.Data;

import java.util.UUID;

@Data
public class TagDto {
    private UUID id;
    private String name;
    private String createdAt;
}
