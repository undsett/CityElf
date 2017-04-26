package com.cityelf.repository;

import com.cityelf.model.Notification;
import com.cityelf.model.User;

/**
 * Created by Sergii on 26.04.2017.
 */
public interface IRepository {
    Notification getNotification(User user);
}
