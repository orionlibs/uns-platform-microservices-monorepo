package io.github.orionlibs.core.api;

import io.github.orionlibs.core.data.DuplicateRecordException;
import io.github.orionlibs.core.data.ResourceNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> onValidationError(MethodArgumentNotValidException ex)
    {
        List<APIField> fields = ex.getBindingResult()
                        .getFieldErrors().stream()
                        .map(fe -> new APIField(
                                        fe.getField(),
                                        fe.getDefaultMessage(),
                                        fe.getRejectedValue()
                        ))
                        .toList();
        APIError body = new APIError(
                        OffsetDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation failed for one or more fields",
                        fields
        );
        log.error("Invalid API input");
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<APIError> onDuplicateRecordException(DuplicateRecordException ex)
    {
        log.error("Duplicate database record found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIError(
                        OffsetDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Duplicate database record found: " + ex.getMessage(),
                        null
        ));
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> onResourceNotFoundException(ResourceNotFoundException ex)
    {
        log.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIError(
                        OffsetDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Resource not found: " + ex.getMessage(),
                        null
        ));
    }


    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<APIError> handleForbiddenExceptions(Exception ex)
    {
        APIError apiError = new APIError(
                        OffsetDateTime.now(),
                        HttpStatus.FORBIDDEN.value(),
                        "Access denied",
                        null
        );
        log.error("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(apiError.status()).body(apiError);
    }


    /*@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIError> handleAllUncheckedExceptions(Exception ex)
    {
        APIError apiError = new APIError(
                        OffsetDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "An unexpected error occurred",
                        null
        );
        log.error("Uncaught unchecked exception: {}", ex.getMessage());
        return ResponseEntity.status(apiError.status()).body(apiError);
    }*/


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleAllCheckedExceptions(Exception ex)
    {
        APIError apiError = new APIError(
                        OffsetDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "An unexpected error occurred",
                        null
        );
        log.error("Uncaught checked exception: {}", ex.getMessage());
        return ResponseEntity.status(apiError.status()).body(apiError);
    }
}
