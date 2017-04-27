package com.cityelf.repository;

import com.cityelf.model.Notification;
import com.cityelf.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository {
    public Notification getNotification(User user){
        return new Notification();
    }
}
