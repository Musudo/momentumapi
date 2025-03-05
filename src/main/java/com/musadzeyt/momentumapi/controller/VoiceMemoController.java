package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.dto.VoiceMemoDto;
import com.musadzeyt.momentumapi.service.VoiceMemoService;
import com.musadzeyt.momentumapi.util.mapper.IVoiceMemoMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/amount")
    public ResponseEntity<List<Integer>> findVoiceMemosAmountPerDayForLastMonth() {
        return new ResponseEntity<>(voiceMemoService.findAmountPerDayForLastMonth(), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<VoiceMemoDto> createVoiceMemo(@RequestBody VoiceMemoDto voiceMemoDto) {
        VoiceMemo voiceMemo = voiceMemoService.create(voiceMemoDto);
        return new ResponseEntity<>(IVoiceMemoMapper.INSTANCE.entityToDto(voiceMemo), HttpStatus.OK);
    }
}
