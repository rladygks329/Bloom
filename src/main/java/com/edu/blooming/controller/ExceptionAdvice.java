package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.edu.blooming.exception.AlreadyExistException;

@ControllerAdvice
public class ExceptionAdvice {
  private Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

  /*
   * @from (Cart Purchase, LectureLike) controller
   */
  @ExceptionHandler(AlreadyExistException.class)
  public ResponseEntity<String> HandleAlreadyExistException(final RuntimeException ex) {
    logger.info("HandleAlreadyExistException() 실행");
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}
