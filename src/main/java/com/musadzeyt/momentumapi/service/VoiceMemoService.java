package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.User;
import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.dto.SearchCriteria;
import com.musadzeyt.momentumapi.dto.entity.VoiceMemoDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IVoiceMemoRepository;
import com.musadzeyt.momentumapi.specification.VoiceMemoSpecification;
import com.musadzeyt.momentumapi.util.mapper.IVoiceMemoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoiceMemoService {
    private final IVoiceMemoRepository voiceMemoRepository;
    private final IVoiceMemoMapper voiceMemoMapper;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    private String getUsername() {
        return customUserDetailsService.getCurrentUsername();
    }

    private Specification<VoiceMemo> getUsernameSpec() {
        SearchCriteria criteria = new SearchCriteria("user.email", ":", getUsername());
        return new VoiceMemoSpecification(criteria);
    }

    public List<VoiceMemo> findAll() {
        return voiceMemoRepository.findAll(getUsernameSpec());
    }

    public List<Map<String, Integer>> findAmountsPerDayForLastMonth() {
        return voiceMemoRepository.findAmountsPerDayForIntervalOfDays(29, getUsername());
    }

    public VoiceMemo findById(UUID id) {
        return voiceMemoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public VoiceMemo create(VoiceMemoDto voiceMemoDto) {
        VoiceMemo voiceMemo = voiceMemoMapper.dtoToEntity(voiceMemoDto);
        User user = userService.findById(voiceMemoDto.getUserId());
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
