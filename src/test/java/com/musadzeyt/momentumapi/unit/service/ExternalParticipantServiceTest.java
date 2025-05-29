package com.musadzeyt.momentumapi.unit.service;

import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.ExternalParticipantRepository;
import com.musadzeyt.momentumapi.service.entityService.ExternalParticipantService;
import com.musadzeyt.momentumapi.util.mapper.ExternalParticipantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalParticipantServiceTest {

    @Mock
    private ExternalParticipantRepository repo;

    @Mock
    private ExternalParticipantMapper mapper;

    @InjectMocks
    private ExternalParticipantService service;

    private ExternalParticipantDto dto;
    private ExternalParticipant entity;

    @BeforeEach
    void setUp() {
        dto = new ExternalParticipantDto();
        dto.setName("Abbas");
        dto.setEmail("abbas@example.com");

        entity = new ExternalParticipant();
        entity.setName("Abbas");
        entity.setEmail("abbas@example.com");
    }

    @Test
    @DisplayName("findById_whenMissing_thenThrowException")
    void findById_whenMissing_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
        verify(repo).findById(id);
    }

    @Test
    @DisplayName("findById_whenExists_thenReturnEntity")
    void findById_whenExists_shouldReturnEntity() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.of(entity));

        ExternalParticipant result = service.findById(id);

        assertSame(entity, result);
        verify(repo).findById(id);
    }

    @Test
    @DisplayName("findAll_shouldReturnAllEntities")
    void findAll_shouldReturnAllEntities() {
        List<ExternalParticipant> expected = List.of(entity);
        when(repo.findAll()).thenReturn(expected);

        List<ExternalParticipant> actual = service.findAll();

        assertEquals(expected, actual);
        verify(repo).findAll();
    }

    @Test
    @DisplayName("findAllById_shouldReturnAllMatchingEntities")
    void findAllById_shouldReturnAllMatchingEntities() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<ExternalParticipant> expected = List.of(entity);
        when(repo.findAllById(List.of(id1, id2))).thenReturn(expected);

        List<ExternalParticipant> actual = service.findAllById(List.of(id1, id2));

        assertEquals(expected, actual);
        verify(repo).findAllById(List.of(id1, id2));
    }

    @Test
    @DisplayName("create_shouldMapAndSaveEntity")
    void create_shouldMapAndSave() {
        when(mapper.dtoToEntity(dto)).thenReturn(entity);

        ExternalParticipant saved = new ExternalParticipant();
        saved.setId(UUID.randomUUID());
        saved.setName(entity.getName());
        saved.setEmail(entity.getEmail());

        when(repo.save(entity)).thenReturn(saved);

        ExternalParticipant result = service.create(dto);

        assertEquals(saved, result);

        InOrder inOrder = inOrder(mapper, repo);
        inOrder.verify(mapper).dtoToEntity(dto);
        inOrder.verify(repo).save(entity);
    }

    @Test
    @DisplayName("update_whenMissing_shouldThrowException")
    void update_whenMissing_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(id, dto));

        verify(repo).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("update_whenExists_shouldMapUpdateAndSaveEntity")
    void update_whenExists_shouldMapUpdateAndSave() {
        UUID id = UUID.randomUUID();
        ExternalParticipant existing = new ExternalParticipant();
        existing.setId(id);
        existing.setName("Old");
        existing.setEmail("old@example.com");

        when(repo.findById(id)).thenReturn(Optional.of(existing));

        doAnswer(invocation -> {
            ExternalParticipantDto dtoArg = invocation.getArgument(0);
            ExternalParticipant entArg = invocation.getArgument(1);
            entArg.setName(dtoArg.getName());
            entArg.setEmail(dtoArg.getEmail());
            return null;
        }).when(mapper).update(dto, existing);

        ExternalParticipant updated = new ExternalParticipant();
        updated.setId(id);
        updated.setName(dto.getName());
        updated.setEmail(dto.getEmail());

        when(repo.save(existing)).thenReturn(updated);

        ExternalParticipant result = service.update(id, dto);

        assertEquals(updated, result);

        InOrder inOrder = inOrder(repo, mapper, repo);
        inOrder.verify(repo).findById(id);
        inOrder.verify(mapper).update(dto, existing);
        inOrder.verify(repo).save(existing);
    }
}
