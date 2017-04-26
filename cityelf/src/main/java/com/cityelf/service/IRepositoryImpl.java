package com.cityelf.service;

import com.cityelf.model.Notification;
import com.cityelf.model.User;
import com.cityelf.repository.IRepository;
import org.springframework.stereotype.Component;

/**
 * Created by Sergii on 26.04.2017.
 */
@Component
public class IRepositoryImpl implements IRepository {
    @Override
    public Notification getNotification(User user) {
        return new Notification();
    }
}
