package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.ErrorLog;
import com.musadzeyt.momentumapi.repository.IErrorLogRepository;
import com.musadzeyt.momentumapi.util.mapper.ITagMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ErrorLogService {
    private final IErrorLogRepository errorLogRepository;
    private final ITagMapper tagMapper;

    public List<ErrorLog> findAll() {
        return errorLogRepository.findAll();
    }

    public ErrorLog create(ErrorLog errorLog) {
        return errorLogRepository.save(errorLog);
    }

    public void delete(UUID id) {
        errorLogRepository.deleteById(id);
    }
}
