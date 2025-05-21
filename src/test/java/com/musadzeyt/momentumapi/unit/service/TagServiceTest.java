package com.musadzeyt.momentumapi.unit.service;

import com.musadzeyt.momentumapi.dataFaker.generator.TagGenerator;
import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.dto.entityDto.TagDto;
import com.musadzeyt.momentumapi.enums.TagNameEnum;
import com.musadzeyt.momentumapi.repository.ITagRepository;
import com.musadzeyt.momentumapi.service.entityService.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private ITagRepository repo;

    @InjectMocks
    private TagService service;

    private Tag entity;

    @BeforeEach
    void setUp() {
        entity = new Tag();
        entity.setName(TagNameEnum.PERSONAL);
    }

    @Test
    @DisplayName("findAll_shouldReturnAllTags")
    void findAll_shouldReturnAllEntities() {
        List<Tag> expected = List.of(entity);
        when(repo.findAll()).thenReturn(expected);

        List<Tag> actual = service.findAll();

        Assertions.assertEquals(expected, actual);
        verify(repo).findAll();
    }

    @Test
    @DisplayName("findAllById_shouldReturnAllMatchingTags")
    void findAllById_shouldReturnAllMatchingEntities() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<Tag> expected = List.of(entity);
        when(repo.findAllById(List.of(id1, id2))).thenReturn(expected);

        List<Tag> actual = service.findAllById(List.of(id1, id2));

        Assertions.assertEquals(expected, actual);
        verify(repo).findAllById(List.of(id1, id2));
    }
}



