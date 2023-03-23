package com.artzvrzn.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
  ADMINISTRATOR, SALE_USER, CUSTOMER_USER, SECURE_API_USER, INVALID;

  @JsonCreator
  public static Role getFromString(String value) {
    if (value == null) {
      return null;
    }
    String modifiedValue;
    if (value.contains(" ")) {
      modifiedValue = value.replaceAll(" ", "_").toUpperCase();
    } else {
      modifiedValue = value.toUpperCase();
    }
    for (Role role: Role.values()) {
      if (role.name().equals(modifiedValue)) {
        return role;
      }
    }
    return INVALID;
  }
}
