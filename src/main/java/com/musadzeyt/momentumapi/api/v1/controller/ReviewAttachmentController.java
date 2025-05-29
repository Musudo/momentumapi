package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ReviewAttachmentDto;
import com.musadzeyt.momentumapi.service.entityService.ReviewAttachmentService;
import com.musadzeyt.momentumapi.util.mapper.ReviewAttachmentMapper;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Hidden
@RestController
@RequestMapping("/api/v1/reviews/attachments")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReviewAttachmentController {
    private final ReviewAttachmentService reviewAttachmentService;

    @GetMapping("")
    public ResponseEntity<List<ReviewAttachmentDto>> findReviewAttachments() {
        return new ResponseEntity<>(ReviewAttachmentMapper.INSTANCE.entityListToDtoList(reviewAttachmentService.findAll()), HttpStatus.OK);
    }
}
