package io.github.orionlibs.documents.api;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.api.APIField;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalValidationHandler
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
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
