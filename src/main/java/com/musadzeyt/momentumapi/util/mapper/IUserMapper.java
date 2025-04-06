package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.User;
import com.musadzeyt.momentumapi.dto.entity.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    @Mapping(target = "id", source = "id")
    UserDto entityToDto(User user);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "password", ignore = true) // This should be generated, so ignore
    User dtoToEntity(UserDto userDto);

    List<UserDto> entityListToDtoList(List<User> list);

    List<User> dtoListToEntityList(List<UserDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    User update(UserDto userDto, @MappingTarget User user);
}