package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.dto.ActivityDto;
import com.musadzeyt.momentumapi.service.ActivityService;
import com.musadzeyt.momentumapi.util.mapper.IActivityMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping("")
    public ResponseEntity<List<ActivityDto>> findActivities() {
        return new ResponseEntity<>(activityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/last30Days")
    public ResponseEntity<List<ActivityDto>> findActivitiesForLast30Days() {
        return new ResponseEntity<>(activityService.findAllForLast30Days(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> findActivity(@PathVariable UUID id) {
        return new ResponseEntity<>(activityService.findActivityDtoById(id), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ActivityDto> createActivity(@RequestBody ActivityDto activityDto) {
        var activity = activityService.create(activityDto);
        return new ResponseEntity<>(IActivityMapper.INSTANCE.entityToDto(activity), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable UUID id, @NotNull @Valid @RequestBody ActivityDto activityDto) {
        var activity = activityService.update(id, activityDto);
        return new ResponseEntity<>(IActivityMapper.INSTANCE.entityToDto(activity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable UUID id) {
        activityService.delete(id);
        return new ResponseEntity<>("Activity deleted", HttpStatus.OK);
    }
}
