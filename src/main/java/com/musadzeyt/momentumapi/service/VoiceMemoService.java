package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.User;
import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.dto.VoiceMemoDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IVoiceMemoRepository;
import com.musadzeyt.momentumapi.util.mapper.IVoiceMemoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoiceMemoService {
    private final IVoiceMemoRepository voiceMemoRepository;
    private final IVoiceMemoMapper voiceMemoMapper;
    private final UserService userService;

    public List<VoiceMemoDto> findAll() {
        List<VoiceMemo> voiceMemos = voiceMemoRepository.findAll();
        return voiceMemoMapper.entityListToDtoList(voiceMemos);
    }

    public VoiceMemoDto findVoiceMemoDtoById(UUID id) {
        VoiceMemo review = voiceMemoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return voiceMemoMapper.entityToDto(review);
    }

    public VoiceMemo findVoiceMemoById(UUID id) {
        return voiceMemoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public VoiceMemo create(VoiceMemoDto voiceMemoDto) {
        VoiceMemo voiceMemo = voiceMemoMapper.dtoToEntity(voiceMemoDto);
        User user = userService.findUserById(voiceMemoDto.getUserId());
        voiceMemo.setUser(user);
        return voiceMemoRepository.save(voiceMemo);
    }

    public VoiceMemo update(UUID id, VoiceMemoDto voiceMemoDto) {
        VoiceMemo voiceMemo = voiceMemoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        voiceMemoMapper.update(voiceMemoDto, voiceMemo);
        return voiceMemoRepository.save(voiceMemo);
    }

    public void delete(UUID id) {
        voiceMemoRepository.deleteById(id);
    }
}
