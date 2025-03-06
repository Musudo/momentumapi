package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.dto.TaskDto;
import com.musadzeyt.momentumapi.service.TaskService;
import com.musadzeyt.momentumapi.util.mapper.ITaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/interval/{days}")
//    public ResponseEntity<List<TaskDto>> findTasksForInterval(@PathVariable int days) {
//        return new ResponseEntity<>(ITaskMapper.INSTANCE.entityListToDtoList(taskService.findAllForInterval(days)), HttpStatus.OK);
//    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskService.create(taskDto);
        return new ResponseEntity<>(ITaskMapper.INSTANCE.entityToDto(task), HttpStatus.OK);
    }
}
