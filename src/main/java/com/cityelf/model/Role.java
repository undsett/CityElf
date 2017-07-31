package com.cityelf.model;

public enum Role {
  ANONIMUS_ROLE(1),
  ADMIN_ROLE(2),
  AUTHORIZED_ROLE(3),
  SYSTEM_ROLE(4),
  UTILITYADMIN_ROLE(5);

  private long id;

  Role(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public static Role createRole(long id) {
    switch ((int) id) {
      case 2:
        return ADMIN_ROLE;
      case 3:
        return AUTHORIZED_ROLE;
      case 4:
        return SYSTEM_ROLE;
      case 5:
        return UTILITYADMIN_ROLE;
      default:
        return ANONIMUS_ROLE;
    }
  }
}
