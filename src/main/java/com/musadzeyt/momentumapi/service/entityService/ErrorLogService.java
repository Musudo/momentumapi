package com.musadzeyt.momentumapi.service.entityService;

import com.musadzeyt.momentumapi.domain.ErrorLog;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ErrorLogDto;
import com.musadzeyt.momentumapi.repository.ErrorLogRepository;
import com.musadzeyt.momentumapi.service.CustomUserDetailsService;
import com.musadzeyt.momentumapi.util.mapper.ErrorLogMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ErrorLogService {
    private final ErrorLogRepository errorLogRepository;
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

    public void createErrorLog(String errorMessage, StackTraceElement[] stackTrace) {
        ErrorLogDto errorLogDto = new ErrorLogDto();
        errorLogDto.setUserId(customUserDetailsService.getCurrentUser().getId());
        errorLogDto.setMessage(errorMessage);

        for (StackTraceElement ste : stackTrace) {
            if (ste.getClassName().startsWith("com.musadzeyt")) {
                errorLogDto.setOriginalClass(ste.getClassName());
                errorLogDto.setOriginalMethod(ste.getMethodName());
                errorLogDto.setOriginalLineNumber(ste.getLineNumber());
                break;
            }
        }

        errorLogRepository.save(ErrorLogMapper.INSTANCE.dtoToEntity(errorLogDto));
    }

    public void delete(UUID id) {
        errorLogRepository.deleteById(id);
    }
}
