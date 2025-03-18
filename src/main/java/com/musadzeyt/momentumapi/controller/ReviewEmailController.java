package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.ReviewEmail;
import com.musadzeyt.momentumapi.dto.ReviewEmailDto;
import com.musadzeyt.momentumapi.service.ReviewEmailService;
import com.musadzeyt.momentumapi.util.mapper.IReviewEmailMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emails")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReviewEmailController {
    private final ReviewEmailService reviewEmailService;

    @GetMapping("")
    public ResponseEntity<List<ReviewEmailDto>> findReviewEmails() {
        return new ResponseEntity<>(IReviewEmailMapper.INSTANCE.entityListToDtoList(reviewEmailService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/interval-of-days/{days}")
    public ResponseEntity<List<ReviewEmailDto>> findReviewEmailsForIntervalOfDays(@PathVariable int days) {
        return new ResponseEntity<>(IReviewEmailMapper.INSTANCE.entityListToDtoList(reviewEmailService.findAllForIntervalOfDays(days)), HttpStatus.OK);
    }

    @GetMapping("/last-month/amounts-per-day")
    public ResponseEntity<List<Map<String, Integer>>> findReviewEmailsAmountsPerDayForLastMonth() {
        return new ResponseEntity<>(reviewEmailService.findAmountsPerDayForLastMonth(), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ReviewEmailDto> createReviewEmail(@RequestBody ReviewEmailDto emailDto) {
        ReviewEmail reviewEmail = reviewEmailService.create(emailDto);
        return new ResponseEntity<>(IReviewEmailMapper.INSTANCE.entityToDto(reviewEmail), HttpStatus.OK);
    }
}
