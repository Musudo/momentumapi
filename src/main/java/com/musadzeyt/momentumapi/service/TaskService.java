package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.dto.TaskDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.ITaskRepository;
import com.musadzeyt.momentumapi.util.mapper.ITaskMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TaskService {
    private final ITaskRepository taskRepository;
    private final ITaskMapper taskMapper;
    private final ActivityService activityService;

    public List<TaskDto> findAll() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.entityListToDtoList(tasks);
    }

    public TaskDto findById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return taskMapper.entityToDto(task);
    }

    public Task create(TaskDto taskDto) {
        Task task = taskMapper.dtoToEntity(taskDto);
        Activity activity = activityService.findActivityById(taskDto.getActivityId());
        task.setActivity(activity);

        return taskRepository.save(task);
    }

    public Task update(UUID id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        taskMapper.update(taskDto, task);
        return taskRepository.save(task);
    }

    public void delete(UUID id) {
        taskRepository.deleteById(id);
    }
}
