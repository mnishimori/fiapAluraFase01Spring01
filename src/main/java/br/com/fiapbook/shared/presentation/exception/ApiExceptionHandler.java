package br.com.fiapbook.shared.presentation.exception;

import br.com.fiapbook.shared.exception.DuplicatedException;
import br.com.fiapbook.shared.exception.ValidatorException;
import br.com.fiapbook.shared.presentation.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(DuplicatedException.class)
  public ResponseEntity handlerDuplicatedException(DuplicatedException duplicatedException) {
    var error = duplicatedException.getMessage();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(ValidatorException.class)
  public ResponseEntity handlerValidatorException(ValidatorException validatorException) {
    var error = validatorException.getMessage();
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handlerMethodArgumentNotValidException(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    var errors = methodArgumentNotValidException.getFieldErrors();
    return ResponseEntity.badRequest().body(errors.stream().map(ErrorDto::new).toList());
  }
}
