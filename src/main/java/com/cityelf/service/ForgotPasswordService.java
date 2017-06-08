package com.cityelf.service;

import com.cityelf.exceptions.TokenNotFoundException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.User;
import com.cityelf.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  MailSenderService mailSender;

  public void forgotPassword(String userEmail) throws UserNotFoundException {
    Optional<User> user = getUserByEmail(userEmail);
    String token = UUID.randomUUID().toString();
    sendResetTokenEmail(userEmail, token);
    user.get().setToken(token);
    user.get().setExpirationDate();
    // tokensRepository.save(new TokenForgotPassword(token, user.get()));
  }

  private Optional<User> getUserByEmail(String userEmail) {//throws UserNotFoundException {
    return Optional.ofNullable(userRepository.findByEmail(userEmail).get());
    //userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException()));
  }

  private void sendResetTokenEmail(String userEmail, String token) {
    mailSender.sendMail(userEmail, "Reset password for CityElf",
        "We heard that you lost your CityElf password..."
            + "\n\nBut don’t worry! You can use the following link within the next day to reset your password:\n"
            + "http://localhost:8088/services/forgot/newPassword?token=" + token
            + "\n\nIf you don’t use this link within 24 hours, it will expire."
            + "\n\nThanks,\nYour friends at CityElf");
  }

  public void settingNewPassword(String token, String newPassword) throws Exception {
    Optional<User> user = userRepository.findByToken(token);
    if (user != null) {
      user.get().setPassword(convertFromStringToMD5(newPassword));
      user.get().setToken(null);
      user.get().setExpirationDate();
    } else {
      throw new TokenNotFoundException();
    }
  }

  private String convertFromStringToMD5(String newPassword) {
    return DigestUtils.md5DigestAsHex(newPassword.getBytes());
  }
}