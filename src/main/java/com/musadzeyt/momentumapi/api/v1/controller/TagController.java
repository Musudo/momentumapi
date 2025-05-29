package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.api.v1.dto.entityDto.TagDto;
import com.musadzeyt.momentumapi.service.entityService.TagService;
import com.musadzeyt.momentumapi.util.mapper.TagMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Tag", description = "Operations related to tags")
@RestController
@RequestMapping("/api/v1/tags")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @Operation(summary = "Get all tags", description = "Returns a list of all tags.")
    @GetMapping("")
    public ResponseEntity<List<TagDto>> findTags() {
        return new ResponseEntity<>(TagMapper.INSTANCE.entityListToDtoList(tagService.findAll()), HttpStatus.OK);
    }
}
