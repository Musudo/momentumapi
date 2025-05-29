package com.musadzeyt.momentumapi.api.v1.dto.entityDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TagDto {
    private UUID id;
    private String name;
    private String createdAt;
}
