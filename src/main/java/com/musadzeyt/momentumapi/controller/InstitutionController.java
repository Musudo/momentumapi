package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.dto.entityDto.InstitutionDto;
import com.musadzeyt.momentumapi.service.entityService.InstitutionService;
import com.musadzeyt.momentumapi.util.mapper.IInstitutionMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/institutions")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;
    private final IInstitutionMapper institutionMapper;

    @GetMapping("")
    public ResponseEntity<List<InstitutionDto>> findInstitutions() {
        return new ResponseEntity<>(institutionMapper.entityListToDtoList(institutionService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDto> findInstitution(@PathVariable UUID id) {
        return new ResponseEntity<>(institutionMapper.entityToDto(institutionService.findById(id)), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<InstitutionDto> createInstitution(@RequestBody InstitutionDto institutionDto) {
        var institution = institutionService.create(institutionDto);
        return new ResponseEntity<>(IInstitutionMapper.INSTANCE.entityToDto(institution), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<InstitutionDto> updateInstitution(@PathVariable UUID id, @NotNull @Valid @RequestBody InstitutionDto institutionDto) {
        var institution = institutionService.update(id, institutionDto);
        return new ResponseEntity<>(IInstitutionMapper.INSTANCE.entityToDto(institution), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable UUID id) {
        institutionService.delete(id);
        return new ResponseEntity<>("Institution deleted", HttpStatus.OK);
    }
}
