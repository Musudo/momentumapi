package com.musadzeyt.momentumapi.api.v1.dto.entityDto;

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
    private List<ReviewAttachmentDto> reviewAttachmentDtos;
    private List<UUID> emailIds;
    private List<ReviewEmailDto> reviewEmailDtos;
}
