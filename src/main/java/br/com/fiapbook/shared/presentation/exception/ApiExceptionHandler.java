package br.com.fiapbook.shared.presentation.exception;

import br.com.fiapbook.shared.exception.DuplicatedException;
import br.com.fiapbook.shared.exception.ValidatorException;
import br.com.fiapbook.shared.presentation.dto.ErrorDto;
import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(DuplicatedException.class)
  public ResponseEntity<?> handlerDuplicatedException(DuplicatedException duplicatedException) {
    var error = duplicatedException.getMessage();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(ValidatorException.class)
  public ResponseEntity<?> handlerValidatorException(ValidatorException validatorException) {
    var error = validatorException.getMessage();
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handlerMethodArgumentNotValidException(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    var errors = methodArgumentNotValidException.getFieldErrors();
    return ResponseEntity.badRequest().body(errors.stream().map(ErrorDto::new).toList());
  }

  @ExceptionHandler(NoResultException.class)
  public ResponseEntity<?> handlerNoResultException(NoResultException noResultException) {
    var error = noResultException.getMessage();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handlerBadRequest(HttpMessageNotReadableException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handlerUnauthorized(AuthenticationException exception) {
    var error = exception.getMessage();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<?> handlerForbbiden(AccessDeniedException exception) {
    var error = exception.getMessage();
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handlerInternalServerError(Exception ex) {
    var error = ex.getMessage();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
