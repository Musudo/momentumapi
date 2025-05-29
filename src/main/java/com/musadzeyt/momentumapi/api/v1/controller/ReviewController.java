package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ReviewDto;
import com.musadzeyt.momentumapi.service.entityService.ReviewService;
import com.musadzeyt.momentumapi.util.mapper.ReviewMapper;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<List<ReviewDto>> findReviews() {
        return new ResponseEntity<>(ReviewMapper.INSTANCE.entityListToDtoList(reviewService.findAll()), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        Review review = reviewService.create(reviewDto);
        return new ResponseEntity<>(ReviewMapper.INSTANCE.entityToDto(review), HttpStatus.OK);
    }
}
