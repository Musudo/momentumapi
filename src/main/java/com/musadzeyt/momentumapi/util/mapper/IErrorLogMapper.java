package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.ErrorLog;
import com.musadzeyt.momentumapi.dto.entityDto.ErrorLogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IErrorLogMapper {
    IErrorLogMapper INSTANCE = Mappers.getMapper(IErrorLogMapper.class);

    @Mapping(target = "userId", source = "user.id")
    ErrorLogDto entityToDto(ErrorLog errorLog);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "timestamp", ignore = true) // This should be generated, so ignore
    @Mapping(target = "user.id", source = "userId")
    ErrorLog dtoToEntity(ErrorLogDto errorLogDto);

    List<ErrorLogDto> entityListToDtoList(List<ErrorLog> list);

    List<ErrorLog> dtoListToEntityList(List<ErrorLogDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    ErrorLog update(ErrorLogDto errorLogDto, @MappingTarget ErrorLog errorLog);
}