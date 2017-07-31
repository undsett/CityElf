package com.cityelf.repository;


import com.cityelf.model.UserRole;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

  List<UserRole> findByUserId(Long userId);

  UserRole save(UserRole userRole);
}
