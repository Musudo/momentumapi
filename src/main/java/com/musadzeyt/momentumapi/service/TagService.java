package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.repository.ITagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TagService {
    private final ITagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public List<Tag> findAllById(List<UUID> ids) {
        return tagRepository.findAllById(ids);
    }
}
