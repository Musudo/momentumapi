package com.musadzeyt.momentumapi.service.entityService;

import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.ExternalParticipantRepository;
import com.musadzeyt.momentumapi.util.mapper.ExternalParticipantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExternalParticipantService {
    private final ExternalParticipantRepository externalParticipantRepository;
    private final ExternalParticipantMapper externalParticipantMapper;

    public List<ExternalParticipant> findAll() {
        return externalParticipantRepository.findAll();
    }

    public List<ExternalParticipant> findAllById(Iterable<UUID> ids) {
        return externalParticipantRepository.findAllById(ids);
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
