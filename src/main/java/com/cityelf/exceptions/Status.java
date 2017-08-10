package com.cityelf.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {

  USER_ADD_IN_DB_OK(001, "User added to DB"),
  USER_EXIST(2, "User with this fireBaseId exist in DB"),
  USER_REGISTRATION_OK(11, "User registration OK"),
  EMAIL_EXIST(12, "User with this E-mail exist in DB"),
  EMAIL_CONFIRMED(21, "Email confirmed"),
  EMAIL_NOT_CONFIRMED(22, "Email didn't confirm"),
  LOGIN_OR_PASSWORD_INCORRECT(31, "Your Login or Password is incorrect"),
  LOGIN_PASSWORD_OK(33, "Your login and password is correct"),
  REPORT_WAS_ADDED(41, "Your report was added to DB"),
  USER_ALREADY_REPORTED(42, "This user already send this report");

  Status(int code, String message) {
    this.code = code;
    this.message = message;
  }

  private int code;
  private String message;

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "{"
        + "code=" + code
        + ", message='" + message + '\''
        + '}';
  }
}
