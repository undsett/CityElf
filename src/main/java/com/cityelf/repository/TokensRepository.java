package com.cityelf.repository;

import com.cityelf.domain.TokenForgotPassword;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class TokensRepository {

  private List<TokenForgotPassword> tokens = new ArrayList();

  public void save(TokenForgotPassword tokenForgotPassword) {
    tokens.add(tokenForgotPassword);
  }

  public void delete(TokenForgotPassword tokenForgotPassword) {
    tokens.remove(tokenForgotPassword);
  }

  public Optional<TokenForgotPassword> findToken(String token) {
    return tokens.stream().filter(
        tokenForgotPassword -> ((tokenForgotPassword.getToken().equals(token))
            && (tokenForgotPassword.getExpirationDate().isAfter(LocalDate.now())))).findFirst();//.after(new Date())))).findFirst();
  }
}
