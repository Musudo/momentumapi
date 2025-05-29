package com.musadzeyt.momentumapi.exception;

import com.musadzeyt.momentumapi.api.v1.dto.ApiError;
import com.musadzeyt.momentumapi.service.entityService.ErrorLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST controllers, providing consistent API error responses
 * and logging of exceptions via {@link ErrorLogService}.
 * <p>
 * Catches various exception types and maps them to appropriate HTTP status codes
 * with a structured {@link ApiError} payload.
 * </p>
 * <ul>
 *   <li>{@link Exception} → 500 Internal Server Error</li>
 *   <li>{@link RuntimeException} → 500 Internal Server Error</li>
 *   <li>{@link MethodArgumentNotValidException} → 400 Bad Request</li>
 *   <li>{@link HttpRequestMethodNotSupportedException} → 405 Method Not Allowed</li>
 *   <li>{@link EntityNotFoundException} → 404 Not Found</li>
 * </ul>
 *
 * @see ApiError
 * @see ErrorLogService
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * Service for persisting error logs when exceptions occur.
     */
    private final ErrorLogService errorLogService;

    /**
     * Handles all uncaught exceptions and returns a generic 500 error response.
     * Logs and persists the root cause message and stack trace.
     *
     * @param e       the exception thrown
     * @param request the current HTTP request
     * @return a ResponseEntity containing an ApiError with status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e, HttpServletRequest request) {
        Throwable rootCause = e;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        errorLogService.createErrorLog(rootCause.getMessage(), rootCause.getStackTrace());
        log.error("An unexpected error occurred", e);

        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("An unexpected error occurred: " + rootCause.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Handles uncaught runtime exceptions, logs and persists the error,
     * and returns a 500 Internal Server Error response.
     *
     * @param e the runtime exception thrown
     * @return a ResponseEntity containing an ApiError with status 500
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException e) {
        errorLogService.createErrorLog(e.getMessage(), e.getStackTrace());
        log.error("Runtime exception occurred", e);

        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("A runtime error occurred: " + e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Handles validation errors for controller method arguments and returns a 400 Bad Request.
     * Aggregates all validation error messages into a single comma-separated string.
     *
     * @param e the MethodArgumentNotValidException thrown when validation fails
     * @return a ResponseEntity containing an ApiError with status 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationError(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(errorMessage)
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handles HTTP method not supported exceptions and returns a 405 Method Not Allowed.
     *
     * @param e the HttpRequestMethodNotSupportedException thrown when an unsupported HTTP method is used
     * @return a ResponseEntity containing an ApiError with status 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    /**
     * Handles entity not found exceptions and returns a 404 Not Found.
     *
     * @param e the EntityNotFoundException thrown when a requested entity does not exist
     * @return a ResponseEntity containing an ApiError with status 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException e) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
