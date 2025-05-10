package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Institution;
import com.musadzeyt.momentumapi.dto.entityDto.InstitutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IInstitutionMapper {
    IInstitutionMapper INSTANCE = Mappers.getMapper(IInstitutionMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", ignore = true)
    InstitutionDto entityToDto(Institution institution);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    Institution dtoToEntity(InstitutionDto institutionDto);

    List<InstitutionDto> entityListToDtoList(List<Institution> list);

    List<Institution> dtoListToEntityList(List<InstitutionDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Institution update(InstitutionDto institutionDto, @MappingTarget Institution institution);
}