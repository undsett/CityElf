package com.cityelf.repository;

import com.cityelf.model.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByEmail(String email);

  User findByToken(String token);

  User findById(long Id);

  User save(User user);

  User findByFirebaseId(String firebaseId);

}