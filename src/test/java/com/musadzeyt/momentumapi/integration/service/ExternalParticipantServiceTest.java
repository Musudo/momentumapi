package com.musadzeyt.momentumapi.integration.service;

import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import com.musadzeyt.momentumapi.dto.entityDto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IExternalParticipantRepository;
import com.musadzeyt.momentumapi.service.entityService.ExternalParticipantService;
import com.musadzeyt.momentumapi.util.mapper.IExternalParticipantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExternalParticipantServiceTest extends AbstractServiceTest<ExternalParticipant> {
    @Mock
    private IExternalParticipantRepository repo;
    @Mock
    private IExternalParticipantMapper mapper;
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

    @Override
    protected String entityName() {
        return "ExternalParticipant";
    }

    @Override
    protected List<ExternalParticipant> mockFindAll() {
        List<ExternalParticipant> list = List.of(entity);
        when(repo.findAll()).thenReturn(list);
        return list;
    }

    @Override
    protected List<ExternalParticipant> mockFindAllById(List<UUID> ids) {
        List<ExternalParticipant> list = List.of(entity);
        when(repo.findAllById(ids)).thenReturn(list);
        return list;
    }

    @Test
    @Override
    @DisplayName("findById_whenMissing_thenThrowException")
    public void findById_whenMissing_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
        verify(repo).findById(id);
    }

    @Test
    @Override
    @DisplayName("findById_whenExists_thenReturnExternalParticipant")
    public void findById_whenExists_shouldReturnEntity() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.of(entity));

        ExternalParticipant result = service.findById(id);

        assertSame(entity, result);
        verify(repo).findById(id);
    }

    @Test
    @Override
    @DisplayName("findAll_shouldReturnAllExternalParticipants")
    public void findAll_shouldReturnAllEntities() {
        List<ExternalParticipant> expected = mockFindAll();
        List<ExternalParticipant> actual = service.findAll();

        assertSame(expected, actual);
        verify(repo).findAll();
        verifyNoMoreInteractions(repo);
    }

    @Test
    @Override
    @DisplayName("findAllById_shouldReturnAllMatchingExternalParticipants")
    public void findAllById_shouldReturnAllMatchingEntities() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<ExternalParticipant> expected = mockFindAllById(List.of(id1, id2));
        List<ExternalParticipant> actual = service.findAllById(List.of(id1, id2));

        assertSame(expected, actual);
        verify(repo).findAllById(List.of(id1, id2));
    }

    @Test
    @Override
    @DisplayName("create_shouldMapAndSaveExternalParticipant")
    public void create_shouldMapAndSave() {
        // map dto → entity
        when(mapper.dtoToEntity(dto)).thenReturn(entity);
        // simulate save returning an entity with generated ID
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
    @Override
    @DisplayName("update_whenMissing_shouldThrowException")
    public void update_whenMissing_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(id, dto));
        verify(repo).findById(id);
        verifyNoMoreInteractions(mapper, repo);
    }

    @Test
    @Override
    @DisplayName("update_whenExists_shouldMapUpdateAndSaveExternalParticipant")
    public void update_whenExists_shouldMapUpdateAndSave() {
        UUID id = UUID.randomUUID();
        // existing in DB
        ExternalParticipant existing = new ExternalParticipant();
        existing.setId(id);
        existing.setName("Old");
        existing.setEmail("old@example.com");
        when(repo.findById(id)).thenReturn(Optional.of(existing));
        // do mapping in-place
        doAnswer(invocation -> {
            ExternalParticipantDto dtoArg = invocation.getArgument(0);
            ExternalParticipant entArg = invocation.getArgument(1);
            entArg.setName(dtoArg.getName());
            entArg.setEmail(dtoArg.getEmail());
            return null;
        }).when(mapper).update(dto, existing);
        // simulate save returning the updated entity
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

