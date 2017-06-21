package com.cityelf.exceptions;

public class GasPageStructureChangedException extends ParserUnavailableException {

  public GasPageStructureChangedException(String message) {
    super(message, null);
  }
}
