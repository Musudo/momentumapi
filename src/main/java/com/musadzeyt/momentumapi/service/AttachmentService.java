package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Attachment;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IAttachmentRepository;
import com.musadzeyt.momentumapi.util.mapper.IAttachmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AttachmentService {
    private final IAttachmentRepository attachmentRepository;
    private final IAttachmentMapper attachmentMapper;
    private final ReviewService reviewService;
    private final CustomUserDetailsService customUserDetailsService;

    private String getUsername() {
        return customUserDetailsService.getCurrentUsername();
    }

    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }

    public List<Map<String, Integer>> findAmountsPerDayForLastMonth() {
        return attachmentRepository.findAmountsPerDayForIntervalOfDays(29, getUsername());
    }

    public Attachment findById(UUID id) {
        return attachmentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

//    public VoiceMemo create(AttachmentDto attachmentDto) {
//        Attachment attachment = attachmentMapper.dtoToEntity(attachmentDto);
//        Review review = reviewService.findReviewById(attachmentDto.getReviewId());
//        review.setAttachments(review);
//        return attachmentRepository.save(attachment);
//    }

    public void delete(UUID id) {
        attachmentRepository.deleteById(id);
    }
}
