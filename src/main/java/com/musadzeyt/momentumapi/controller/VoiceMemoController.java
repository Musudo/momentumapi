package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.dto.entity.VoiceMemoDto;
import com.musadzeyt.momentumapi.service.VoiceMemoService;
import com.musadzeyt.momentumapi.util.mapper.IVoiceMemoMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/voice-memos")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class VoiceMemoController {
    private final VoiceMemoService voiceMemoService;

    @GetMapping("")
    public ResponseEntity<List<VoiceMemoDto>> findVoiceMemos() {
        return new ResponseEntity<>(IVoiceMemoMapper.INSTANCE.entityListToDtoList(voiceMemoService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/last-month/amounts")
    public ResponseEntity<List<Map<String, Integer>>> findVoiceMemoAmountsPerDayForLastMonth() {
        return new ResponseEntity<>(voiceMemoService.findAmountsPerDayForLastMonth(), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<VoiceMemoDto> createVoiceMemo(@RequestBody VoiceMemoDto voiceMemoDto) {
        VoiceMemo voiceMemo = voiceMemoService.create(voiceMemoDto);
        return new ResponseEntity<>(IVoiceMemoMapper.INSTANCE.entityToDto(voiceMemo), HttpStatus.OK);
    }
}
