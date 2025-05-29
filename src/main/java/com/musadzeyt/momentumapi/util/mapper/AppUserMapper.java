package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.AppUser;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.AppUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    @Mapping(target = "id", source = "id")
    AppUserDto entityToDto(AppUser user);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "password", ignore = true) // This should be generated, so ignore
    AppUser dtoToEntity(AppUserDto userDto);

    List<AppUserDto> entityListToDtoList(List<AppUser> list);

    List<AppUser> dtoListToEntityList(List<AppUserDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    AppUser update(AppUserDto userDto, @MappingTarget AppUser user);
}