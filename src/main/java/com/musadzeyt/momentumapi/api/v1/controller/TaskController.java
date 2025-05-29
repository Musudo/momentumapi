package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.TaskDto;
import com.musadzeyt.momentumapi.service.entityService.TaskService;
import com.musadzeyt.momentumapi.util.mapper.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Task", description = "Operations related to tasks")
@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Get all tasks", description = "Returns a list of all tasks.")
    @GetMapping("")
    public ResponseEntity<List<TaskDto>> findTasks() {
        return new ResponseEntity<>(TaskMapper.INSTANCE.entityListToDtoList(taskService.findAll()), HttpStatus.OK);
    }

    @Operation(summary = "Get tasks by activity ID", description = "Get tasks by their related activity ID")
    @GetMapping("/by-activity-id/{id}")
    public ResponseEntity<List<TaskDto>> findTasksByActivityId(@PathVariable UUID id) {
        return new ResponseEntity<>(TaskMapper.INSTANCE.entityListToDtoList(taskService.findAllByActivityId(id)), HttpStatus.OK);
    }

    @Operation(summary = "Get tasks by activity subject", description = "Get tasks by their related activity subject")
    @GetMapping("/by-activity-subject/{subject}")
    public ResponseEntity<List<TaskDto>> findTasksByActivitySubject(@PathVariable String subject) {
        return new ResponseEntity<>(TaskMapper.INSTANCE.entityListToDtoList(taskService.findAllByActivitySubject(subject)), HttpStatus.OK);
    }

    @Operation(summary = "Create a new task")
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskService.create(taskDto);
        return new ResponseEntity<>(TaskMapper.INSTANCE.entityToDto(task), HttpStatus.OK);
    }

    @Operation(summary = "Update a task")
    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<TaskDto> updateTask(@PathVariable UUID id, @RequestBody TaskDto taskDto) {
        Task task = taskService.update(id, taskDto);
        return new ResponseEntity<>(TaskMapper.INSTANCE.entityToDto(task), HttpStatus.OK);
    }

    @Operation(summary = "Delete a task")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
        taskService.delete(id);
        return new ResponseEntity<>("Task deleted", HttpStatus.OK);
    }
}
