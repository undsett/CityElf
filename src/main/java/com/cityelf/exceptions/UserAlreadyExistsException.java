package com.cityelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This user already exists!")
public class UserAlreadyExistsException extends UserException {

}
