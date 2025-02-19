package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.dto.ActivityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IActivityMapper {
    IActivityMapper INSTANCE = Mappers.getMapper(IActivityMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "institutionId", source = "institution.id")
    ActivityDto entityToDto(Activity activity);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    Activity dtoToEntity(ActivityDto activityDto);

    List<ActivityDto> entityListToDtoList(List<Activity> list);

    List<Activity> dtoListToEntityList(List<ActivityDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Activity update(ActivityDto activityDto, @MappingTarget Activity activity);
}