package com.musadzeyt.momentumapi.service.entityService;

import com.musadzeyt.momentumapi.domain.Institution;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.InstitutionDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.InstitutionRepository;
import com.musadzeyt.momentumapi.util.mapper.InstitutionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InstitutionService {
    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

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
