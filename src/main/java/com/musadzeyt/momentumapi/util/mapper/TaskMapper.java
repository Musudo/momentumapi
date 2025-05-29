package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ActivityMapper.class, AppUserMapper.class})
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "activityId", source = "activity.id")
    @Mapping(target = "userId", source = "user.id")
    TaskDto entityToDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activity.id", source = "activityId")
    @Mapping(target = "user.id", source = "userId")
    Task dtoToEntity(TaskDto taskDto);

    List<TaskDto> entityListToDtoList(List<Task> list);

    List<Task> dtoListToEntityList(List<TaskDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activity.id", source = "activityId")
    @Mapping(target = "user.id", source = "userId")
    Task update(TaskDto taskDto, @MappingTarget Task tag);
}
