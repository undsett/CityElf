package com.cityelf.exceptions;

import java.io.IOException;

public class WaterParserUnavailableException extends IOException {

  public WaterParserUnavailableException(String message, Throwable cause) {
    super(message, cause);
  }
}
