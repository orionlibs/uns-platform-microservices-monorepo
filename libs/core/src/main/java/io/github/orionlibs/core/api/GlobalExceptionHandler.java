package io.github.orionlibs.core.api;

import io.github.orionlibs.core.data.DuplicateRecordException;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                        ex.getMessage(),
                        null
        ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleAllUncheckedExceptions(Exception ex)
    {
        APIError apiError = new APIError(
                        OffsetDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "An unexpected error occurred",
                        null
        );
        log.error("Uncaught exception: {}", ex.getMessage());
        return ResponseEntity.status(apiError.status()).body(apiError);
    }
}
