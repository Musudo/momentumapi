package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITaskMapper {
    ITaskMapper INSTANCE = Mappers.getMapper(ITaskMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "activityId", source = "activity.id")
    TaskDto entityToDto(Task task);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    Task dtoToEntity(TaskDto taskDto);

    List<TaskDto> entityListToDtoList(List<Task> list);

    List<Task> dtoListToEntityList(List<TaskDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task update(TaskDto taskDto, @MappingTarget Task tag);
}