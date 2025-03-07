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
    private final IActivityMapper activityMapper;

    @GetMapping("")
    public ResponseEntity<List<ActivityDto>> findActivities() {
        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findAll()), HttpStatus.OK);
    }

//    @GetMapping("/type/{type}")
//    public ResponseEntity<List<ActivityDto>> findActivitiesByType(@PathVariable String type) {
//        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findByType(type)), HttpStatus.OK);
//    }

//    @GetMapping("/type/{type}/last-six-months")
//    public ResponseEntity<List<ActivityDto>> findActivitiesByTypeForLastSixMonths(@PathVariable String type) {
//        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findByTypeForInterval(type, false, 180)), HttpStatus.OK);
//    }

//    @GetMapping("/type/{type}/last-six-months/amounts-per-month")
//    public ResponseEntity<List<Integer>> findActivitiesAmountsByTypePerMonthForLastSixMonths(@PathVariable String type) {
//        return new ResponseEntity<>(activityService.findAmountsByTypePerMonthForLastSixMonths(type), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> findActivity(@PathVariable UUID id) {
        return new ResponseEntity<>(activityMapper.entityToDto(activityService.findById(id)), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ActivityDto> createActivity(@RequestBody ActivityDto activityDto) {
        var activity = activityService.create(activityDto);
        return new ResponseEntity<>(activityMapper.entityToDto(activity), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable UUID id, @NotNull @Valid @RequestBody ActivityDto activityDto) {
        var activity = activityService.update(id, activityDto);
        return new ResponseEntity<>(activityMapper.entityToDto(activity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable UUID id) {
        activityService.delete(id);
        return new ResponseEntity<>("Activity deleted", HttpStatus.OK);
    }
}
