package com.musadzeyt.momentumapi.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private List<UUID> institutionIds;
    private List<UUID> activityIds;
    private List<UUID> reviewIds;
    private List<UUID> contactIds;
    private List<UUID> voiceMemoIds;
}
