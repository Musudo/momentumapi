package com.musadzeyt.momentumapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ActivityRecord(
        UUID id,
        String type,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String subject,
        String internalNote,
        String externalNote,
        LocalDateTime emailSentAt,
        List<UUID> externalParticipantIds,
        List<UUID> taskIds,
        List<UUID> reviewIds,
        List<UUID> tagIds
) {
}
