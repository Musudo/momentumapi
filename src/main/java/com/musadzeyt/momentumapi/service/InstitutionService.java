package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Institution;
import com.musadzeyt.momentumapi.dto.InstitutionDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IInstitutionRepository;
import com.musadzeyt.momentumapi.util.mapper.IInstitutionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InstitutionService {
    private final IInstitutionRepository institutionRepository;
    private final IInstitutionMapper institutionMapper;

    public List<Institution> findAll() {
        return institutionRepository.findAll();
    }

    public Institution findById(UUID id) {
        return institutionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Institution findByName(String name) {
        return institutionRepository.findByName(name)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Institution create(InstitutionDto institutionDto) {
        Institution institution = institutionMapper.dtoToEntity(institutionDto);
        return institutionRepository.save(institution);
    }

    public Institution update(UUID id, InstitutionDto institutionDto) {
        Institution institution = institutionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        institutionMapper.update(institutionDto, institution);
        return institutionRepository.save(institution);
    }

    public void delete(UUID id) {
        institutionRepository.deleteById(id);
    }
}
