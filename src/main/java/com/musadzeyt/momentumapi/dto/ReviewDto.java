package com.musadzeyt.momentumapi.dto;

import com.musadzeyt.momentumapi.domain.Attachment;
import com.musadzeyt.momentumapi.domain.Email;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ReviewDto {
    private UUID id;
    private String title;
    private String content;
    private UUID activityId;
    private List<UUID> attachmentIds;
    private List<AttachmentDto> attachments;
    private List<UUID> emailIds;
    private List<EmailDto> emails;
}
