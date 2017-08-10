package com.cityelf.service;

import com.cityelf.model.User;
import com.cityelf.model.UserRole;
import com.cityelf.repository.UserRepository;
import com.cityelf.repository.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SecurityService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserRoleRepository userRoleRepository;

  public User getUserFromSession() {
    return userRepository
        .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
  }

  public Set<UserRole> getRoleOfUserFromSession() {
    return userRoleRepository.findByUserId(getUserFromSession().getId());
  }
}