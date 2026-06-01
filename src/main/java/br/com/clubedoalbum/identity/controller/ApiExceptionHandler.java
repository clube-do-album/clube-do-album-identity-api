package br.com.clubedoalbum.identity.controller;

import br.com.clubedoalbum.identity.dto.ErrorResponse;
import br.com.clubedoalbum.identity.exception.InvalidCredentialsException;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
    List<String> details = exception.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .toList();

    return ResponseEntity.badRequest().body(new ErrorResponse(
        Instant.now(),
        "Validation failed.",
        details
    ));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(
        Instant.now(),
        exception.getMessage(),
        List.of()
    ));
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(
        Instant.now(),
        exception.getMessage(),
        List.of()
    ));
  }
}
