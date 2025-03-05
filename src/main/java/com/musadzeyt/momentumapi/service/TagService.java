package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.dto.TagDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.ITagRepository;
import com.musadzeyt.momentumapi.util.mapper.ITagMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TagService {
    private final ITagRepository tagRepository;
    private final ITagMapper tagMapper;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findTagDtoById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Tag findTagById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Tag create(TagDto tagDto) {
        Tag tag = tagMapper.dtoToEntity(tagDto);
        return tagRepository.save(tag);
    }

    public Tag update(UUID id, TagDto tagDto) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        tagMapper.update(tagDto, tag);
        return tagRepository.save(tag);
    }

    public void delete(UUID id) {
        tagRepository.deleteById(id);
    }
}
