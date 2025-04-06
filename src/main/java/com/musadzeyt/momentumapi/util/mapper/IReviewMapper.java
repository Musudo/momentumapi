package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.domain.ReviewAttachment;
import com.musadzeyt.momentumapi.domain.ReviewEmail;
import com.musadzeyt.momentumapi.dto.entity.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {IReviewAttachmentMapper.class, IReviewEmailMapper.class})
public interface IReviewMapper {
    IReviewMapper INSTANCE = Mappers.getMapper(IReviewMapper.class);

    @Mapping(target = "activityId", source = "activity.id")
    @Mapping(target = "attachmentIds", expression = "java(mapAttachmentIds(review.getReviewAttachments()))")
    @Mapping(target = "reviewAttachmentDtos", source = "reviewAttachments")
    @Mapping(target = "emailIds", expression = "java(mapEmailIds(review.getReviewEmails()))")
    @Mapping(target = "reviewEmailDtos", source = "reviewEmails")
    ReviewDto entityToDto(Review review);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "reviewAttachments", source = "reviewAttachmentDtos")
    @Mapping(target = "reviewEmails", source = "reviewEmailDtos")
    @Mapping(target = "activity.id", source = "activityId")
    Review dtoToEntity(ReviewDto reviewDto);

    List<ReviewDto> entityListToDtoList(List<Review> list);

    List<Review> dtoListToEntityList(List<ReviewDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "reviewAttachments", source = "reviewAttachmentDtos")
    @Mapping(target = "reviewEmails", source = "reviewEmailDtos")
    @Mapping(target = "activity.id", source = "activityId")
    Review update(ReviewDto reviewDto, @MappingTarget Review review);

    default List<UUID> mapAttachmentIds(List<ReviewAttachment> attachments) {
        return attachments == null ? null : attachments.stream().map(ReviewAttachment::getId).toList();
    }

    default List<UUID> mapEmailIds(List<ReviewEmail> emails) {
        return emails == null ? null : emails.stream().map(ReviewEmail::getId).toList();
    }
}