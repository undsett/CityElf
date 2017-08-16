package com.cityelf.repository;

import com.cityelf.model.User;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByEmail(String email);

  User findByToken(String token);

  Optional<User> findByFirebaseId(String firebaseId);

  Optional<User> findById(long id);

  void deleteUserById(Long id);

}