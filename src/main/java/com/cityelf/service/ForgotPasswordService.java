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
  MailSenderService mailSender;

  @Transactional
  public void forgotPassword(String userEmail) throws UserNotFoundException {
    User user = getUserByEmail(userEmail);
    String token = UUID.randomUUID().toString();
    user.setToken(token);
    user.setExpirationDate();
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
    mailSender.sendMail(userEmail, "Reset password for CityElf",
        "We heard that you lost your CityElf password..."
            + "\n\nBut don’t worry! You can use the following link within the next day to reset your password:\n"
            + "http://localhost:8088/services/forgot/resetPassword.html?token=" + token
            + "\n\nIf you don’t use this link within 24 hours, it will expire."
            + "\n\nThanks,\nYour friends at CityElf");
  }

  @Transactional
  public void settingNewPassword(String token, String newPassword) throws Exception {
    User user = userRepository.findByToken(token);
    if (user != null) {
      if (user.getToken().equals(token) && user.getExpirationDate().isAfter(LocalDateTime.now())) {
        user.setPassword(newPassword);
        user.setToken(null);
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