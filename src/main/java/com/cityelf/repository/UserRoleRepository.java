package com.cityelf.repository;


import com.cityelf.model.UserRole;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

  UserRole findByUserId(Long userId);

  UserRole save(UserRole userRole);

  @Transactional
  void deleteUserRoleByUserId(Long userId);
}
