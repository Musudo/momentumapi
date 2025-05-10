package com.musadzeyt.momentumapi.integration.service;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.dto.entityDto.TagDto;
import com.musadzeyt.momentumapi.enums.TagNameEnum;
import com.musadzeyt.momentumapi.repository.ITagRepository;
import com.musadzeyt.momentumapi.service.entityService.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class TagServiceTest extends AbstractServiceTest<Tag> {
    @Mock
    private ITagRepository repo;
    @InjectMocks
    private TagService service;
    private TagDto dto;
    private Tag entity;

    @BeforeEach
    void setUp() {
        dto = new TagDto();
        dto.setName(TagNameEnum.WORK.name());
        entity = new Tag();
        entity.setName(TagNameEnum.WORK);
    }

    @Override
    protected String entityName() {
        return "Tag";
    }

    @Override
    protected List<Tag> mockFindAll() {
        List<Tag> list = List.of(entity);
        when(repo.findAll()).thenReturn(list);
        return list;
    }

    @Override
    protected List<Tag> mockFindAllById(List<UUID> ids) {
        List<Tag> list = List.of(entity);
        when(repo.findAllById(ids)).thenReturn(list);
        return list;
    }

    @Test
    @Override
    @DisplayName("findAll_shouldReturnAllTags")
    public void findAll_shouldReturnAllEntities() {
        List<Tag> expected = mockFindAll();
        List<Tag> actual = service.findAll();

        assertSame(expected, actual);
        verify(repo).findAll();
        verifyNoMoreInteractions(repo);
    }

    @Test
    @Override
    @DisplayName("findAllById_shouldReturnAllMatchingTags")
    public void findAllById_shouldReturnAllMatchingEntities() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<Tag> expected = mockFindAllById(List.of(id1, id2));
        List<Tag> actual = service.findAllById(List.of(id1, id2));

        assertSame(expected, actual);
        verify(repo).findAllById(List.of(id1, id2));
    }
}


