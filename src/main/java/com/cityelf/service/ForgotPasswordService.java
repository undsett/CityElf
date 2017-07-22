package com.cityelf.service;

import com.cityelf.exceptions.TokenNotFoundException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.User;
import com.cityelf.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgotPasswordService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MailSenderService mailSender;

  @Transactional
  public void forgotPassword(String userEmail) throws UserNotFoundException {
    User user = getUserByEmail(userEmail);
    String token = UUID.randomUUID().toString();
    user.setToken(token);
    user.setExpirationDate(LocalDateTime.now().plusDays(1));
    userRepository.save(user);
    sendResetTokenEmail(userEmail, token);
  }

  private User getUserByEmail(String userEmail) throws UserNotFoundException {
    User user = userRepository.findByEmail(userEmail);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return user;
  }

  private void sendResetTokenEmail(String userEmail, String token) {
    mailSender.sendMail(userEmail, "Сброс пароля CityElf",
        "Мы узнали, что ваш пароль был утерян..."
            + "\n\nНо не стоит беспокоиться! Вы можете использовать ссылку в течении 24 часов для его восстановления:\n"
            + "http://localhost:8088/services/forgot/resetPassword.html?token=" + token
            + "\n\nЕсли ссылка не будет использована, она будет аннулирована."
            + "\n\nС уважением,\nкоманда проекта CityElf");
  }

  @Transactional
  public void settingNewPassword(String token, String newPassword) throws Exception {
    User user = userRepository.findByToken(token);
    if (user != null) {
      if (user.getToken().equals(token) && user.getExpirationDate().isBefore(LocalDateTime.now().plusDays(1))) {
        user.setPassword(newPassword);
        user.setToken(null);
        user.setExpirationDate(null);
        userRepository.save(user);
      }
    } else {
      throw new TokenNotFoundException();
    }
  }

  private String convertFromStringToMD5(String newPassword) {
    return DigestUtils.md5DigestAsHex(newPassword.getBytes());
  }
}