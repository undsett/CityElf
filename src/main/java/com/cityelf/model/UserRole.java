package com.cityelf.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {

  @Id
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
}
