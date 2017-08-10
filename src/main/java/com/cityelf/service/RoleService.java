package com.cityelf.service;

import com.cityelf.model.UserRole;
import com.cityelf.repository.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {

  @Autowired
  private UserRoleRepository userRoleRepository;

  public Set<UserRole> getUserRoles(long id) {
    return userRoleRepository.findByUserId(id);
  }

  public UserRole save(UserRole userRole) {
    return userRoleRepository.save(userRole);
  }

  public Iterable<UserRole> save(Set<UserRole> userRoles) {
    return userRoleRepository.save(userRoles);
  }
}
