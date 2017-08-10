package com.cityelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This forecast is not found in the system")
public class ForecastNotFoundException extends Exception {

}
