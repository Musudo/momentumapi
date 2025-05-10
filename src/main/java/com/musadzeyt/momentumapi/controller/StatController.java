package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.dto.statDto.*;
import com.musadzeyt.momentumapi.repository.IActivityRepository;
import com.musadzeyt.momentumapi.service.StatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stat")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class StatController {
    private final StatService statService;
    private final IActivityRepository activityRepository;

    @GetMapping("/activities/last-month/amounts-per-day")
    public ResponseEntity<StatCard> createActivitiesStatCard() {
        var activitiesStatCard = statService.createActivitiesStatCard();
        return new ResponseEntity<>(activitiesStatCard, HttpStatus.OK);
    }

    @GetMapping("/tasks/last-month/amounts-per-day")
    public ResponseEntity<StatCard> createTasksStatCard() {
        var tasksStatCard = statService.createTasksStatCard();
        return new ResponseEntity<>(tasksStatCard, HttpStatus.OK);
    }

    @GetMapping("/reviews/last-month/amounts-per-day")
    public ResponseEntity<StatCard> createReviewsStatCard() {
        var reviewsStatCard = statService.createReviewsStatCard();
        return new ResponseEntity<>(reviewsStatCard, HttpStatus.OK);
    }

    @GetMapping("/activities/last-six-months/amounts-per-month")
    public ResponseEntity<BarChart> createActivitiesBarChart() {
        var activitiesBarChart = statService.createActivityTypesBarChart();
        return new ResponseEntity<>(activitiesBarChart, HttpStatus.OK);
    }

    @GetMapping("/line-chart-data/last-month/amounts-per-day")
    public ResponseEntity<LineChart> createLineChart() {
        var lineChart = statService.createLineChart();
        return new ResponseEntity<>(lineChart, HttpStatus.OK);
    }

    @GetMapping("/contacts-table-data")
    public ResponseEntity<List<ContactTableData>> createContactTableData() {
        var contactTableData = statService.createContactTableData();
        return new ResponseEntity<>(contactTableData, HttpStatus.OK);
    }

    @GetMapping("/institutions-table-data")
    public ResponseEntity<List<InstitutionTableData>> createInstitutionTableData() {
        var institutionTableData = statService.createInstitutionTableData();
        return new ResponseEntity<>(institutionTableData, HttpStatus.OK);
    }
}
