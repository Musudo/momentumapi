package com.musadzeyt.momentumapi.integration.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractServiceTest<T> {
    protected abstract String entityName();

    protected abstract List<T> mockFindAll();

    protected abstract List<T> mockFindAllById(List<UUID> ids);

    public void findById_whenMissing_shouldThrowException() {
        throw new UnsupportedOperationException("Must override findById_whenMissing_shouldThrowException");
    }

    public void findById_whenExists_shouldReturnEntity() {
        throw new UnsupportedOperationException("Must override findById_whenExists_shouldReturnEntity");
    }

    public void findAll_shouldReturnAllEntities() {
        throw new UnsupportedOperationException("Must override findAll_shouldReturnAllEntities");
    }

    public void findAllById_shouldReturnAllMatchingEntities() {
        throw new UnsupportedOperationException("Must override findAllById_shouldReturnAllMatchingEntities");
    }

    public void create_shouldMapAndSave() {
        throw new UnsupportedOperationException("Must override create_shouldMapAndSave");
    }

    public void update_whenMissing_shouldThrowException() {
        throw new UnsupportedOperationException("Must override update_whenMissing_shouldThrowException");
    }

    public void update_whenExists_shouldMapUpdateAndSave() {
        throw new UnsupportedOperationException("Must override update_whenExists_shouldMapUpdateAndSave");
    }
}

