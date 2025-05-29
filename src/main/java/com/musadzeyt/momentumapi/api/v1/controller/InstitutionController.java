package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.api.v1.dto.entityDto.InstitutionDto;
import com.musadzeyt.momentumapi.service.entityService.InstitutionService;
import com.musadzeyt.momentumapi.util.mapper.InstitutionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Institution", description = "Operations related to institutions")
@RestController
@RequestMapping("/api/v1/institutions")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @Operation(summary = "Get all institutions", description = "Returns a list of all institutions.")
    @GetMapping("")
    public ResponseEntity<List<InstitutionDto>> findInstitutions() {
        return new ResponseEntity<>(institutionMapper.entityListToDtoList(institutionService.findAll()), HttpStatus.OK);
    }

    @Operation(summary = "Get an institution by ID")
    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDto> findInstitution(@PathVariable UUID id) {
        return new ResponseEntity<>(institutionMapper.entityToDto(institutionService.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Create a new institution")
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<InstitutionDto> createInstitution(@RequestBody InstitutionDto institutionDto) {
        var institution = institutionService.create(institutionDto);
        return new ResponseEntity<>(InstitutionMapper.INSTANCE.entityToDto(institution), HttpStatus.OK);
    }

    @Operation(summary = "Update an institution")
    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<InstitutionDto> updateInstitution(@PathVariable UUID id, @NotNull @Valid @RequestBody InstitutionDto institutionDto) {
        var institution = institutionService.update(id, institutionDto);
        return new ResponseEntity<>(InstitutionMapper.INSTANCE.entityToDto(institution), HttpStatus.OK);
    }

    @Operation(summary = "Delete an institution")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable UUID id) {
        institutionService.delete(id);
        return new ResponseEntity<>("Institution deleted", HttpStatus.OK);
    }
}
