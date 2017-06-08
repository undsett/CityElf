package com.cityelf.controller;

import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.service.ForgotPasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forgot")
public class ForgotPasswordController {

  @Autowired
  private ForgotPasswordService forgotPasswordService;
/*
  @RequestMapping(value = "/reset", method = RequestMethod.POST)
  public void forgotPassword(@RequestBody String userEmail) throws UserNotFoundException {
    forgotPasswordService.forgotPassword(userEmail);
  }*/

  @RequestMapping(value = "/newPassword", method = RequestMethod.POST)
  public void enterNewPassword(@RequestParam String token, @RequestParam String newPassword)
      throws Exception {
    forgotPasswordService.settingNewPassword(token, newPassword);
  }
}