package com.cityelf.repository;


import com.cityelf.model.UserRole;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

  Set<UserRole> findByUserId(Long userId);
}
