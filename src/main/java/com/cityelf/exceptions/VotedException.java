package com.cityelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class VotedException extends VotingUnavailableException {
  public VotedException(String message) {
    super(message);
  }
}
