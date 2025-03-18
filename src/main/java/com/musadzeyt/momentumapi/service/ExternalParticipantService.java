package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import com.musadzeyt.momentumapi.dto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IExternalParticipantRepository;
import com.musadzeyt.momentumapi.util.mapper.IExternalParticipantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExternalParticipantService {
    private final IExternalParticipantRepository externalParticipantRepository;
    private final IExternalParticipantMapper externalParticipantMapper;
//    private final ActivityService activityService;

    public List<ExternalParticipant> findAll() {
        return externalParticipantRepository.findAll();
    }

    public ExternalParticipant findById(UUID id) {
        return externalParticipantRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public ExternalParticipant create(ExternalParticipantDto externalParticipantDto) {
        ExternalParticipant externalParticipant = externalParticipantMapper.dtoToEntity(externalParticipantDto);
        return externalParticipantRepository.save(externalParticipant);
    }

    public ExternalParticipant update(UUID id, ExternalParticipantDto externalParticipantDto) {
        ExternalParticipant externalParticipant = externalParticipantRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        externalParticipantMapper.update(externalParticipantDto, externalParticipant);
        return externalParticipantRepository.save(externalParticipant);
    }
}
