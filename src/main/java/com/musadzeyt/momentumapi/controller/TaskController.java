package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.dto.entityDto.TaskDto;
import com.musadzeyt.momentumapi.service.entityService.TaskService;
import com.musadzeyt.momentumapi.util.mapper.ITaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("")
    public ResponseEntity<List<TaskDto>> findTasks() {
        return new ResponseEntity<>(ITaskMapper.INSTANCE.entityListToDtoList(taskService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/by-activity-id/{id}")
    public ResponseEntity<List<TaskDto>> findTasksByActivityId(@PathVariable UUID id) {
        return new ResponseEntity<>(ITaskMapper.INSTANCE.entityListToDtoList(taskService.findAllByActivityId(id)), HttpStatus.OK);
    }

    @GetMapping("/by-activity-subject/{subject}")
    public ResponseEntity<List<TaskDto>> findTasksByActivitySubject(@PathVariable String subject) {
        return new ResponseEntity<>(ITaskMapper.INSTANCE.entityListToDtoList(taskService.findAllByActivitySubject(subject)), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskService.create(taskDto);
        return new ResponseEntity<>(ITaskMapper.INSTANCE.entityToDto(task), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<TaskDto> updateTask(@PathVariable UUID id, @RequestBody TaskDto taskDto) {
        Task task = taskService.update(id, taskDto);
        return new ResponseEntity<>(ITaskMapper.INSTANCE.entityToDto(task), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
        taskService.delete(id);
        return new ResponseEntity<>("Task deleted", HttpStatus.OK);
    }
}
