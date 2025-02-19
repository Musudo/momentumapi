package com.musadzeyt.momentumapi.dto;

import com.musadzeyt.momentumapi.domain.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ActivityDto {
    private UUID id;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String subject;
    private String internalNote;
    private String externalNote;
    private LocalDateTime emailSentAt;
    private UUID userId;
    private UUID institutionId;
    private Institution institution;
    private List<UUID> tagIds;
    private List<Tag> tags;
    private List<UUID> contactIds;
    private List<Contact> contacts;
    private List<UUID> externalParticipantIds;
    private List<ExternalParticipant> externalParticipants;
}
