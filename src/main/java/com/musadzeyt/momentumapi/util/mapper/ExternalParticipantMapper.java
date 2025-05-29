package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ExternalParticipantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExternalParticipantMapper {
    ExternalParticipantMapper INSTANCE = Mappers.getMapper(ExternalParticipantMapper.class);

//    @Mapping(target = "id", source = "id")
    ExternalParticipantDto entityToDto(ExternalParticipant externalParticipant);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    ExternalParticipant dtoToEntity(ExternalParticipantDto externalParticipantDto);

    List<ExternalParticipantDto> entityListToDtoList(List<ExternalParticipant> list);

    List<ExternalParticipant> dtoListToEntityList(List<ExternalParticipantDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ExternalParticipant update(ExternalParticipantDto externalParticipantDto, @MappingTarget ExternalParticipant externalParticipant);
}