package com.musadzeyt.momentumapi.service.entityService;

import com.musadzeyt.momentumapi.domain.ReviewEmail;
import com.musadzeyt.momentumapi.dto.entityDto.ReviewEmailDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IReviewEmailRepository;
import com.musadzeyt.momentumapi.service.CustomUserDetailsService;
import com.musadzeyt.momentumapi.util.mapper.IReviewEmailMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewEmailService {
    private final IReviewEmailRepository emailRepository;
    private final IReviewEmailMapper reviewEmailMapper;
    private final CustomUserDetailsService customUserDetailsService;

    private String getUsername() {
        return customUserDetailsService.getCurrentUsername();
    }

    public List<ReviewEmail> findAll() {
        return emailRepository.findAll();
    }

    public List<ReviewEmail> findAllForIntervalOfDays(int days) {
        return emailRepository.findAllForIntervalOfDays(days);
    }

    public List<Map<String, Integer>> findAmountsPerDayForLastMonth() {
        return emailRepository.findAmountsPerDayForIntervalOfDays(29, getUsername());
    }

    public ReviewEmail findById(UUID id) {
        return emailRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public ReviewEmail create(ReviewEmailDto reviewEmailDto) {
        ReviewEmail reviewEmail = reviewEmailMapper.dtoToEntity(reviewEmailDto);
        return emailRepository.save(reviewEmail);
    }

    public ReviewEmail update(UUID id, ReviewEmailDto reviewEmailDto) {
        ReviewEmail reviewEmail = emailRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        reviewEmailMapper.update(reviewEmailDto, reviewEmail);
        return emailRepository.save(reviewEmail);
    }

    public void delete(UUID id) {
        emailRepository.deleteById(id);
    }
}
