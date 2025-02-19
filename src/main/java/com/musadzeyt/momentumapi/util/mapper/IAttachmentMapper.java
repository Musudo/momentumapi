package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Attachment;
import com.musadzeyt.momentumapi.dto.AttachmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAttachmentMapper {
    IAttachmentMapper INSTANCE = Mappers.getMapper(IAttachmentMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "reviewId", source = "review.id")
    AttachmentDto entityToDto(Attachment attachment);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    Attachment dtoToEntity(AttachmentDto attachmentDto);

    List<AttachmentDto> entityListToDtoList(List<Attachment> list);

    List<Attachment> dtoListToEntityList(List<AttachmentDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Attachment update(AttachmentDto attachmentDto, @MappingTarget Attachment attachment);
}