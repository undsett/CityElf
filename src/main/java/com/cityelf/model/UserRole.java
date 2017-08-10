package com.cityelf.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;
  @Column(name = "user_id")
  private long userId;
  @Column(name = "role_id")
  private long roleId;

  public UserRole() {
  }

  public UserRole(long userId, long roleId) {
    this.userId = userId;
    this.roleId = roleId;
  }

  public UserRole(long userId, Role role) {
    this.userId = userId;
    this.roleId = role.getId();
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }

  public void setRole(Role role) {
    this.roleId = role.getId();
  }

  public Role getRole() {
    return Role.createRole(roleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, roleId);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    UserRole anotherUserRole = (UserRole) obj;
    return Objects.equals(userId, anotherUserRole.getUserId())
        && Objects.equals(roleId, anotherUserRole.getRoleId());
  }
}
