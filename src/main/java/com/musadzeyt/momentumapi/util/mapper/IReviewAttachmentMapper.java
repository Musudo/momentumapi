package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.ReviewAttachment;
import com.musadzeyt.momentumapi.dto.ReviewAttachmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IReviewAttachmentMapper {
    IReviewAttachmentMapper INSTANCE = Mappers.getMapper(IReviewAttachmentMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "reviewId", source = "review.id")
    ReviewAttachmentDto entityToDto(ReviewAttachment reviewAttachment);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "review", ignore = true)
    ReviewAttachment dtoToEntity(ReviewAttachmentDto attachmentDto);

    List<ReviewAttachmentDto> entityListToDtoList(List<ReviewAttachment> list);

    List<ReviewAttachment> dtoListToEntityList(List<ReviewAttachmentDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "review", ignore = true)
    ReviewAttachment update(ReviewAttachmentDto reviewAttachmentDto, @MappingTarget ReviewAttachment reviewAttachment);
}