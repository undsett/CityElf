package com.cityelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FeedbackMessageException extends Exception {

  public FeedbackMessageException(String message) {
    super(message);
  }
}
