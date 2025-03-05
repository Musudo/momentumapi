package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.dto.TagDto;
import com.musadzeyt.momentumapi.service.TagService;
import com.musadzeyt.momentumapi.util.mapper.ITagMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("")
    public ResponseEntity<List<TagDto>> findTags() {
        return new ResponseEntity<>(ITagMapper.INSTANCE.entityListToDtoList(tagService.findAll()), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        Tag tag = tagService.create(tagDto);
        return new ResponseEntity<>(ITagMapper.INSTANCE.entityToDto(tag), HttpStatus.OK);
    }
}
