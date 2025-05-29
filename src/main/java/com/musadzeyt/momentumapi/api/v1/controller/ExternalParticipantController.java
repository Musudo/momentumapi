package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.service.entityService.ExternalParticipantService;
import com.musadzeyt.momentumapi.util.mapper.ExternalParticipantMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "External participant", description = "Operations related to external participants")
@RestController
@RequestMapping("/api/v1/external-participants")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ExternalParticipantController {
    private final ExternalParticipantService externalParticipantService;

    @Operation(summary = "Get all external participants", description = "Returns a list of all external participants.")
    @GetMapping("")
    public ResponseEntity<List<ExternalParticipantDto>> findExternalParticipants() {
        return new ResponseEntity<>(ExternalParticipantMapper.INSTANCE.entityListToDtoList(externalParticipantService.findAll()), HttpStatus.OK);
    }
}
