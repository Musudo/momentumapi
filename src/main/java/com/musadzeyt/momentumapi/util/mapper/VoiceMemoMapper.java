package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.VoiceMemoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoiceMemoMapper {
    VoiceMemoMapper INSTANCE = Mappers.getMapper(VoiceMemoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "completed", ignore = true)
    VoiceMemoDto entityToDto(VoiceMemo voiceMemo);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "user", ignore = true) // This should be generated, so ignore
    VoiceMemo dtoToEntity(VoiceMemoDto voiceMemoDto);

    List<VoiceMemoDto> entityListToDtoList(List<VoiceMemo> list);

    List<VoiceMemo> dtoListToEntityList(List<VoiceMemoDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    VoiceMemo update(VoiceMemoDto voiceMemoDto, @MappingTarget VoiceMemo voiceMemo);
}