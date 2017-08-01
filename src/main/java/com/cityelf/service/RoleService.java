package com.cityelf.service;

import com.cityelf.model.Role;
import com.cityelf.model.UserRole;
import com.cityelf.repository.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

  @Autowired
  private UserRoleRepository userRoleRepository;

  public Set<Role> getRolesByUserId(long id) {
    List<UserRole> userRoles = userRoleRepository.findByUserId(id);
    return userRoles == null
        ? new HashSet<>()
        : userRoles
            .stream()
            .map(userRole -> Role.createRole(userRole.getRoleId()))
            .collect(Collectors.toSet());
  }

  public UserRole saveRole(long userId, Role role) {
    return userRoleRepository.save(new UserRole(userId, role));
  }

  public Iterable<UserRole> saveRole(final long userId, Set<Role> roles) {
    List<UserRole> userRoles = roles
        .stream()
        .map(role -> new UserRole(userId, role))
        .collect(Collectors.toList());
    return userRoleRepository.save(userRoles);
  }
}
