package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Attachment;
import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.domain.User;
import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.dto.AttachmentDto;
import com.musadzeyt.momentumapi.dto.VoiceMemoDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IAttachmentRepository;
import com.musadzeyt.momentumapi.repository.IVoiceMemoRepository;
import com.musadzeyt.momentumapi.util.mapper.IAttachmentMapper;
import com.musadzeyt.momentumapi.util.mapper.IVoiceMemoMapper;
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

    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }

    public List<Map<String, Integer>> findAmountsPerDayForLastMonth() {
        return attachmentRepository.findAmountsPerDayForIntervalOfDays(29);
    }

    public Attachment findAttachmentById(UUID id) {
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
