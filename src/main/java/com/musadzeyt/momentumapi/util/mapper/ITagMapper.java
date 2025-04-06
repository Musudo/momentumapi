package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.dto.entity.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITagMapper {
    ITagMapper INSTANCE = Mappers.getMapper(ITagMapper.class);

    @Mapping(target = "id", source = "id")
    TagDto entityToDto(Tag tag);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    Tag dtoToEntity(TagDto tagDto);

    List<TagDto> entityListToDtoList(List<Tag> list);

    List<Tag> dtoListToEntityList(List<TagDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Tag update(TagDto tagDto, @MappingTarget Tag tag);
}