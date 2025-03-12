package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.dto.SearchCriteria;
import com.musadzeyt.momentumapi.dto.TaskDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.ITaskRepository;
import com.musadzeyt.momentumapi.specification.TaskSpecification;
import com.musadzeyt.momentumapi.util.mapper.ITaskMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TaskService {
    private final ITaskRepository taskRepository;
    private final ITaskMapper taskMapper;
    private final ActivityService activityService;
    private final CustomUserDetailsService customUserDetailsService;

    private String getUsername() {
        return customUserDetailsService.getCurrentUsername();
    }

    private Specification<Task> getUsernameSpec() {
        SearchCriteria criteria = new SearchCriteria("user.email", ":", getUsername());
        return new TaskSpecification(criteria);
    }

    public List<Task> findAll() {
        return taskRepository.findAll(getUsernameSpec());
    }

    public List<Map<String, Integer>> findAmountsPerDayForLastMonth() {
        return taskRepository.findAmountsPerDayForIntervalOfDays(29, getUsername());
    }

    public List<Task> findAllByActivity(String subject) {
        SearchCriteria taskCriteria = new SearchCriteria("activity.subject", ":", subject);
        Specification<Task> taskSpec = new TaskSpecification(taskCriteria);

        return taskRepository.findAll(getUsernameSpec().and(taskSpec));
    }

    public Task findById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Task create(TaskDto taskDto) {
        Task task = taskMapper.dtoToEntity(taskDto);
        Activity activity = activityService.findById(taskDto.getActivityId());
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
