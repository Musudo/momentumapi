package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import com.musadzeyt.momentumapi.dto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.service.ExternalParticipantService;
import com.musadzeyt.momentumapi.util.mapper.IExternalParticipantMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/externalParticipants")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ExternalParticipantController {
    private final ExternalParticipantService externalParticipantService;

    @GetMapping("")
    public ResponseEntity<List<ExternalParticipantDto>> findExternalParticipants() {
        return new ResponseEntity<>(externalParticipantService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ExternalParticipantDto> createExternalParticipant(@RequestBody ExternalParticipantDto externalParticipantDto) {
        ExternalParticipant externalParticipant = externalParticipantService.create(externalParticipantDto);
        return new ResponseEntity<>(IExternalParticipantMapper.INSTANCE.entityToDto(externalParticipant), HttpStatus.OK);
    }
}
