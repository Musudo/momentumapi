package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import com.musadzeyt.momentumapi.dto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IExternalParticipantRepository;
import com.musadzeyt.momentumapi.util.mapper.IExternalParticipantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExternalParticipantService {
    private final IExternalParticipantRepository externalParticipantRepository;
    private final IExternalParticipantMapper externalParticipantMapper;

    public List<ExternalParticipant> findAll() {
        return externalParticipantRepository.findAll();
    }

    public ExternalParticipant findExternalParticipantDtoById(UUID id) {
        return externalParticipantRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public ExternalParticipant findExternalParticipantById(UUID id) {
        return externalParticipantRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public ExternalParticipant create(ExternalParticipantDto externalParticipantDto) {
        ExternalParticipant tag = externalParticipantMapper.dtoToEntity(externalParticipantDto);
        return externalParticipantRepository.save(tag);
    }

    public ExternalParticipant update(UUID id, ExternalParticipantDto externalParticipantDto) {
        ExternalParticipant externalParticipant = externalParticipantRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        externalParticipantMapper.update(externalParticipantDto, externalParticipant);
        return externalParticipantRepository.save(externalParticipant);
    }

    public void delete(UUID id) {
        externalParticipantRepository.deleteById(id);
    }
}
