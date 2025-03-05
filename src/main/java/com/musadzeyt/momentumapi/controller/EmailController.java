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
        return new ResponseEntity<>(IEmailMapper.INSTANCE.entityListToDtoList(emailService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/interval/{days}")
    public ResponseEntity<List<EmailDto>> findEmailsForInterval(@PathVariable int days) {
        return new ResponseEntity<>(IEmailMapper.INSTANCE.entityListToDtoList(emailService.findAllForInterval(days)), HttpStatus.OK);
    }

    @GetMapping("/amount")
    public ResponseEntity<List<Integer>> findEmailsAmountPerDayForLastMonth() {
        return new ResponseEntity<>(emailService.findAmountPerDayForLastMonth(), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<EmailDto> createEmail(@RequestBody EmailDto emailDto) {
        Email email = emailService.create(emailDto);
        return new ResponseEntity<>(IEmailMapper.INSTANCE.entityToDto(email), HttpStatus.OK);
    }
}
