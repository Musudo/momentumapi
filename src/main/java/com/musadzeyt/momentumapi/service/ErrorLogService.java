package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.ErrorLog;
import com.musadzeyt.momentumapi.repository.IErrorLogRepository;
import com.musadzeyt.momentumapi.util.mapper.IErrorLogMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ErrorLogService {
    private final IErrorLogRepository errorLogRepository;
    private final IErrorLogMapper errorLogMapper;
    private final CustomUserDetailsService customUserDetailsService;

    private String getUsername() {
        return customUserDetailsService.getCurrentUsername();
    }

    public List<ErrorLog> findAll() {
        return errorLogRepository.findAll();
    }

    public List<ErrorLog> findAllByUser() {
        return errorLogRepository.findAllByUser(getUsername());
    }

    public ErrorLog create(ErrorLog errorLog) {
        return errorLogRepository.save(errorLog);
    }

    public void delete(UUID id) {
        errorLogRepository.deleteById(id);
    }
}
