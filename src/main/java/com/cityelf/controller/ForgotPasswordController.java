package com.cityelf.controller;

import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.service.ForgotPasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/forgot")
public class ForgotPasswordController {

  @Autowired
  private ForgotPasswordService forgotPasswordService;

  @RequestMapping(value = "/reset", method = RequestMethod.POST)
  public void forgotPassword(@RequestParam(name = "email") String email)
      throws UserNotFoundException {
    forgotPasswordService.forgotPassword(email);
  }

  @RequestMapping(value = "/newpassword", method = RequestMethod.POST)
  public void enterNewPassword(@RequestParam(name = "token") String token,
      @RequestParam(name = "newpassword") String newpassword)
      throws Exception {
    forgotPasswordService.settingNewPassword(token, newpassword);
  }
}