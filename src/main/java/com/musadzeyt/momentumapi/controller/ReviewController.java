package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.dto.entityDto.ReviewDto;
import com.musadzeyt.momentumapi.service.entityService.ReviewService;
import com.musadzeyt.momentumapi.util.mapper.IReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<List<ReviewDto>> findReviews() {
        return new ResponseEntity<>(IReviewMapper.INSTANCE.entityListToDtoList(reviewService.findAll()), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        Review review = reviewService.create(reviewDto);
        return new ResponseEntity<>(IReviewMapper.INSTANCE.entityToDto(review), HttpStatus.OK);
    }
}
