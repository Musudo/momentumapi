package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.dto.VoiceMemoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVoiceMemoMapper {
    IVoiceMemoMapper INSTANCE = Mappers.getMapper(IVoiceMemoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    VoiceMemoDto entityToDto(VoiceMemo voiceMemo);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    VoiceMemo dtoToEntity(VoiceMemoDto voiceMemoDto);

    List<VoiceMemoDto> entityListToDtoList(List<VoiceMemo> list);

    List<VoiceMemo> dtoListToEntityList(List<VoiceMemoDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    VoiceMemo update(VoiceMemoDto voiceMemoDto, @MappingTarget VoiceMemo voiceMemo);
}