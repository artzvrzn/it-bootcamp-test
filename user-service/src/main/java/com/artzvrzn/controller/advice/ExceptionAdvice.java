package com.artzvrzn.controller.advice;

import com.artzvrzn.dto.error.ErrorResponse;
import com.artzvrzn.dto.error.StructuredErrorResponse;
import com.artzvrzn.exception.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ExceptionAdvice {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<?> validationHandler(ValidationException exc) {
    if (exc.getViolations() == null || exc.getViolations().isEmpty()) {
      log.warn("validation exception with no violations occurred");
      ErrorResponse body = new ErrorResponse("request contains invalid data");
      return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(new StructuredErrorResponse(exc.getViolations()),
      HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse exceptionHandler(IllegalArgumentException exc) {
    log.error("{}: {}", exc.getClass().getSimpleName(), exc.getMessage());
    return new ErrorResponse(exc.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse exceptionHandler(Exception exc) {
    log.error("{}: {}", exc.getClass().getSimpleName(), exc.getMessage());
    return new ErrorResponse("server failed to process the request");
  }
}
