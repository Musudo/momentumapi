package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.api.v1.dto.statDto.*;
import com.musadzeyt.momentumapi.repository.ActivityRepository;
import com.musadzeyt.momentumapi.service.StatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Stat", description = "Operations related to stats")
@RestController
@RequestMapping("/api/v1/stat")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class StatController {
    private final StatService statService;
    private final ActivityRepository activityRepository;

    @Operation(summary = "Create activities stat card")
    @GetMapping("/activities/last-month/amounts-per-day")
    public ResponseEntity<StatCard> createActivitiesStatCard() {
        var activitiesStatCard = statService.createActivitiesStatCard();
        return new ResponseEntity<>(activitiesStatCard, HttpStatus.OK);
    }

    @Operation(summary = "Create tasks stat card")
    @GetMapping("/tasks/last-month/amounts-per-day")
    public ResponseEntity<StatCard> createTasksStatCard() {
        var tasksStatCard = statService.createTasksStatCard();
        return new ResponseEntity<>(tasksStatCard, HttpStatus.OK);
    }

    @Operation(summary = "Create reviews stat card")
    @GetMapping("/reviews/last-month/amounts-per-day")
    public ResponseEntity<StatCard> createReviewsStatCard() {
        var reviewsStatCard = statService.createReviewsStatCard();
        return new ResponseEntity<>(reviewsStatCard, HttpStatus.OK);
    }

    @Operation(summary = "Create activities bar chart")
    @GetMapping("/activities/last-six-months/amounts-per-month")
    public ResponseEntity<BarChart> createActivitiesBarChart() {
        var activitiesBarChart = statService.createActivityTypesBarChart();
        return new ResponseEntity<>(activitiesBarChart, HttpStatus.OK);
    }

    @Operation(summary = "Create line chart")
    @GetMapping("/line-chart-data/last-month/amounts-per-day")
    public ResponseEntity<LineChart> createLineChart() {
        var lineChart = statService.createLineChart();
        return new ResponseEntity<>(lineChart, HttpStatus.OK);
    }

    @Operation(summary = "Create contact table data")
    @GetMapping("/contacts-table-data")
    public ResponseEntity<List<ContactTableData>> createContactTableData() {
        var contactTableData = statService.createContactTableData();
        return new ResponseEntity<>(contactTableData, HttpStatus.OK);
    }

    @Operation(summary = "Create institution table data")
    @GetMapping("/institutions-table-data")
    public ResponseEntity<List<InstitutionTableData>> createInstitutionTableData() {
        var institutionTableData = statService.createInstitutionTableData();
        return new ResponseEntity<>(institutionTableData, HttpStatus.OK);
    }
}
