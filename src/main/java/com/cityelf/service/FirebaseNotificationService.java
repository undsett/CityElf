package com.cityelf.service;

import com.cityelf.model.Forecast;
import com.cityelf.model.GasForecast;
import com.cityelf.model.NotificationToken;
import com.cityelf.model.User;
import com.cityelf.model.WaterForecast;
import com.cityelf.repository.FirebaseNotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class FirebaseNotificationService {

  @Autowired
  FirebaseNotificationRepository firebaseNotificationRepository;

  @Autowired
  AppServerFirebase appServerFirebase;

  String webUserFirebaseId = "WEB";

  public void firebaseNotificate(Collection<Forecast> forecasts) {
    for (Forecast forecast : forecasts) {
      startNotification(forecast);
    }
    cleaner(LocalDateTime.now().minusHours(24));
  }

  private void startNotification(Forecast forecast) {
    NotificationToken notificationToken;
    List<User> users = forecast.getAddress().getUsers();
    if (users != null) {
      for (User user : users) {
        if (!user.getFirebaseId().equals(webUserFirebaseId)) {
          notificationToken = new NotificationToken(forecast.getAddress().getAddress(),
              forecast.getStart(),
              forecast.getEstimatedStop(), user.getId());
          if (firebaseNotificationRepository.findByTextHash(notificationToken.getTextHash())
              == null) {
            try {
              appServerFirebase.pushFCMNotification(user.getFirebaseId(), "New shutdown",
                  createMessageToFirebase(forecast));
              firebaseNotificationRepository.save(notificationToken);
            } catch (Exception exception) {
              exception.printStackTrace();
            }
          }
        }
      }
    }
  }

  private String createMessageToFirebase(Forecast forecast) {
    String typeOfForecast;
    if (forecast instanceof GasForecast) {
      typeOfForecast = " будет отключен газ ";
    } else if (forecast instanceof WaterForecast) {
      typeOfForecast = " будет отключено водоснабжение ";
    } else {
      typeOfForecast = " будет отключено электричество ";
    }
    return "По адресу " + forecast.getAddress().getAddress() + typeOfForecast
        + "предположительно с " + formatDateTime(forecast.getStart()) + " до " + formatDateTime(
        forecast.getEstimatedStop());
  }

  private String formatDateTime(LocalDateTime localDateTime) {
    return localDateTime.toLocalDate().toString() + " " + localDateTime.toLocalTime().toString();
  }

  private void cleaner(LocalDateTime time) {
    firebaseNotificationRepository.deleteByTimeOfEntryBefore(time);
  }
}