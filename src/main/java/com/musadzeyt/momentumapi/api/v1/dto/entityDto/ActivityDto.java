package com.musadzeyt.momentumapi.api.v1.dto.entityDto;

import com.musadzeyt.momentumapi.enums.ActivityType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ActivityDto {
    private UUID id;
    private ActivityType type;
    private String startTime;
    private String endTime;
    private String subject;
    private String internalNote;
    private String externalNote;
    private String emailSentAt;
    private UUID userId;
    private String institutionName;
    private UUID institutionId;
    private InstitutionDto institution;
    private List<UUID> tagIds;
    private List<TagDto> tags;
    private List<UUID> contactIds;
    private List<ContactDto> contacts;
    private List<UUID> externalParticipantIds;
    private List<ExternalParticipantDto> externalParticipants;
    private String createdAt;
}
