package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.VoiceMemoDto;
import com.musadzeyt.momentumapi.service.entityService.VoiceMemoService;
import com.musadzeyt.momentumapi.util.mapper.VoiceMemoMapper;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Hidden
@RestController
@RequestMapping("/api/v1/voice-memos")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class VoiceMemoController {
    private final VoiceMemoService voiceMemoService;

    @GetMapping("")
    public ResponseEntity<List<VoiceMemoDto>> findVoiceMemos() {
        return new ResponseEntity<>(VoiceMemoMapper.INSTANCE.entityListToDtoList(voiceMemoService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/last-month/amounts")
    public ResponseEntity<List<Map<String, Integer>>> findVoiceMemoAmountsPerDayForLastMonth() {
        return new ResponseEntity<>(voiceMemoService.findAmountsPerDayForLastMonth(), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<VoiceMemoDto> createVoiceMemo(@RequestBody VoiceMemoDto voiceMemoDto) {
        VoiceMemo voiceMemo = voiceMemoService.create(voiceMemoDto);
        return new ResponseEntity<>(VoiceMemoMapper.INSTANCE.entityToDto(voiceMemo), HttpStatus.OK);
    }
}
