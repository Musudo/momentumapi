package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.dto.ReviewDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IReviewRepository;
import com.musadzeyt.momentumapi.util.mapper.IReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewService {
    private final IReviewRepository reviewRepository;
    private final IReviewMapper reviewMapper;
    private final ActivityService activityService;

    public List<ReviewDto> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviewMapper.entityListToDtoList(reviews);
    }

    public ReviewDto findReviewDtoById(UUID id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return reviewMapper.entityToDto(review);
    }

    public Review findReviewById(UUID id) {
        return reviewRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Review create(ReviewDto reviewDto) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        Activity activity = activityService.findActivityById(reviewDto.getActivityId());
        review.setActivity(activity);
        return reviewRepository.save(review);
    }

    public Review update(UUID id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        reviewMapper.update(reviewDto, review);
        return reviewRepository.save(review);
    }

    public void delete(UUID id) {
        reviewRepository.deleteById(id);
    }
}
