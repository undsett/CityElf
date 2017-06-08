package com.cityelf.repository;

import com.cityelf.model.User;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByEmail(String userEmail);
  Optional<User> findByToken(String token);
}