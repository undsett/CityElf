package com.cityelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN,
    reason = "Access is denied. You don't have the necessary rights to perform this operation")
public class AccessDeniedException extends Exception {

}
