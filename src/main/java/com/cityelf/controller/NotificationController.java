package com.cityelf.controller;

import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.Notification;
import com.cityelf.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/notification")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @RequestMapping(value = "/settings/{userId}", method = RequestMethod.GET)
  public Notification getNotifications(@PathVariable("userId") long id)
      throws UserNotFoundException {
    if (!notificationService.getNotification(id).isPresent()) {
      throw new UserNotFoundException();
    }
    return notificationService.getNotification(id).get();
  }
}