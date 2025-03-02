package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.Email;
import com.musadzeyt.momentumapi.dto.EmailDto;
import com.musadzeyt.momentumapi.service.EmailService;
import com.musadzeyt.momentumapi.util.mapper.IEmailMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emails")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("")
    public ResponseEntity<List<EmailDto>> findEmails() {
        return new ResponseEntity<>(emailService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/last30Days")
    public ResponseEntity<List<EmailDto>> findEmailsForLast30Days() {
        return new ResponseEntity<>(emailService.findAllForLast30Days(), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<EmailDto> createEmail(@RequestBody EmailDto emailDto) {
        Email email = emailService.create(emailDto);
        return new ResponseEntity<>(IEmailMapper.INSTANCE.entityToDto(email), HttpStatus.OK);
    }
}
