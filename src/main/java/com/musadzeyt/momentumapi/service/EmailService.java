package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Email;
import com.musadzeyt.momentumapi.dto.EmailDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IEmailRepository;
import com.musadzeyt.momentumapi.util.mapper.IEmailMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailService {
    private final IEmailRepository emailRepository;
    private final IEmailMapper emailMapper;

    public List<Email> findAll() {
        return emailRepository.findAll();
    }

    public List<Email> findAllForIntervalOfDays(int days) {
        return emailRepository.findAllForIntervalOfDays(days);
    }

    public List<Map<String, Integer>> findAmountsPerDayForLastMonth() {
        return emailRepository.findAmountsPerDayForIntervalOfDays(29);
    }

    public Email findById(UUID id) {
        return emailRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Email create(EmailDto emailDto) {
        Email email = emailMapper.dtoToEntity(emailDto);
        return emailRepository.save(email);
    }

    public Email update(UUID id, EmailDto emailDto) {
        Email email = emailRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        emailMapper.update(emailDto, email);
        return emailRepository.save(email);
    }

    public void delete(UUID id) {
        emailRepository.deleteById(id);
    }
}
