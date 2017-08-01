package com.cityelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class VotingUnavailableException extends Exception {

  public VotingUnavailableException(String message) {
    super(message);
  }

  public VotingUnavailableException(String message, Throwable cause) {
    super(message, cause);
  }
}
