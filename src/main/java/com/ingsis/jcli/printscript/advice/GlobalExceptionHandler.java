package com.ingsis.jcli.printscript.advice;

import com.ingsis.jcli.printscript.common.Generated;
import com.ingsis.jcli.printscript.common.exceptions.SnippetNotFoundException;
import com.ingsis.jcli.printscript.common.exceptions.VersionNotValid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@Generated
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(SnippetNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<String> handleInvalidSnippetException(
      SnippetNotFoundException ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(VersionNotValid.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleInvalidVersion(VersionNotValid ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
