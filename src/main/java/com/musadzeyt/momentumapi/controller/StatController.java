package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.dto.stat.BarChartDto;
import com.musadzeyt.momentumapi.dto.stat.LineChartDto;
import com.musadzeyt.momentumapi.dto.stat.StatCardDto;
import com.musadzeyt.momentumapi.repository.IActivityRepository;
import com.musadzeyt.momentumapi.service.StatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stat")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class StatController {
    private final StatService statService;
    private final IActivityRepository activityRepository;

    @GetMapping("/activities/last-month/amounts-per-day")
    public ResponseEntity<StatCardDto> createActivitiesStatCardDto() {
        var activitiesStatCardDto = statService.createActivitiesStatCardDto();
        return new ResponseEntity<>(activitiesStatCardDto, HttpStatus.OK);
    }

    @GetMapping("/tasks/last-month/amounts-per-day")
    public ResponseEntity<StatCardDto> createTasksStatCardDto() {
        var tasksStatCardDto = statService.createTasksStatCardDto();
        return new ResponseEntity<>(tasksStatCardDto, HttpStatus.OK);
    }

    @GetMapping("/reviews/last-month/amounts-per-day")
    public ResponseEntity<StatCardDto> createReviewsStatCardDto() {
        var reviewsStatCardDto = statService.createReviewsStatCardDto();
        return new ResponseEntity<>(reviewsStatCardDto, HttpStatus.OK);
    }

    @GetMapping("/activities/last-six-months/amounts-per-month")
    public ResponseEntity<BarChartDto> createActivitiesBarChartDto() {
        var activitiesBarChartDto = statService.createActivitiesBarChartDto();
        return new ResponseEntity<>(activitiesBarChartDto, HttpStatus.OK);
    }

    @GetMapping("/line-chart-data/last-month/amounts-per-day")
    public ResponseEntity<LineChartDto> createLineChartDto() {
        var lineChartDto = statService.createLineChartDto();
        return new ResponseEntity<>(lineChartDto, HttpStatus.OK);
    }
}
