package com.cityelf.controller;

import com.cityelf.model.User;
import com.cityelf.model.Notification;
import com.cityelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private UserService us;

    @RequestMapping("/settings")
    public Notification getNotifications(User user) {
        return us.getNotification(user);
    }
}