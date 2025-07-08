package com.musadzeyt.momentumapi.api.v1.dto.entityDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private UUID id;
    private String name;
    private String createdAt;
}
