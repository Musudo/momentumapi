package com.musadzeyt.momentumapi.service.entityService;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.dto.entityDto.ReviewDto;
import com.musadzeyt.momentumapi.dto.SearchCriteria;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IReviewRepository;
import com.musadzeyt.momentumapi.service.CustomUserDetailsService;
import com.musadzeyt.momentumapi.specification.ReviewSpecification;
import com.musadzeyt.momentumapi.util.mapper.IReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewService {
    private final IReviewRepository reviewRepository;
    private final IReviewMapper reviewMapper;
    private final ActivityService activityService;
    private final CustomUserDetailsService customUserDetailsService;

    private String getUsername() {
        return customUserDetailsService.getCurrentUsername();
    }

    private Specification<Review> getUsernameSpec() {
        SearchCriteria criteria = new SearchCriteria("user.email", ":", getUsername());
        return new ReviewSpecification(criteria);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll(getUsernameSpec());
    }

    public List<Map<String, Integer>> findAmountsPerDayForLastMonth() {
        return reviewRepository.findAmountsPerDayForIntervalOfDays(29, getUsername());
    }

    public Review findById(UUID id) {
        return reviewRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Review create(ReviewDto reviewDto) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        Activity activity = activityService.findById(reviewDto.getActivityId());
        review.setActivity(activity);
        return reviewRepository.save(review);
    }

    @Transactional
    public Review update(UUID id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        reviewMapper.update(reviewDto, review);
        return reviewRepository.save(review);
    }

    @Transactional
    public void delete(UUID id) {
        Review review = reviewRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        review.setActivity(null);
        review.setUser(null);
        review.getReviewEmails().clear();
        review.getReviewAttachments().clear();

        reviewRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        List<Review> reviews = reviewRepository.findAll();
        for (Review review : reviews) {
            delete(review.getId());
        }
    }
}
