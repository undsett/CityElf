package com.cityelf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No advertisements on this address")
public class AdvertisementNotFoundException extends Exception {

}