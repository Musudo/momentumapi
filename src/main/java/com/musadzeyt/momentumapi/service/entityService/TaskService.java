package com.musadzeyt.momentumapi.service.entityService;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.domain.AppUser;
import com.musadzeyt.momentumapi.api.v1.dto.SearchCriteria;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.TaskDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.TaskRepository;
import com.musadzeyt.momentumapi.service.CustomUserDetailsService;
import com.musadzeyt.momentumapi.specification.TaskSpecification;
import com.musadzeyt.momentumapi.util.mapper.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ActivityService activityService;
    private final AppUserService userService;
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

    public List<Task> findAllByActivityId(UUID id) {
        SearchCriteria taskCriteria = new SearchCriteria("activity.id", ":", id);
        Specification<Task> taskSpec = new TaskSpecification(taskCriteria);

        return taskRepository.findAll(getUsernameSpec().and(taskSpec));
    }

    public List<Task> findAllByActivitySubject(String subject) {
        SearchCriteria taskCriteria = new SearchCriteria("activity.subject", ":", subject);
        Specification<Task> taskSpec = new TaskSpecification(taskCriteria);

        return taskRepository.findAll(getUsernameSpec().and(taskSpec));
    }

    public Task findById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Task create(TaskDto taskDto) {
        Task task = taskMapper.dtoToEntity(taskDto);

        Activity activity = activityService.findById(taskDto.getActivityId());
        task.setActivity(activity);

        AppUser user = userService.findByEmail(getUsername());
        task.setUser(user);

        return taskRepository.save(task);
    }

    @Transactional
    public Task update(UUID id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        taskMapper.update(taskDto, task);
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteAllByActivityId(UUID activityId) {
        taskRepository.deleteAllByActivityId(activityId);
    }

    @Transactional
    public void delete(UUID id) {
        taskRepository.deleteById(id);
    }
}
