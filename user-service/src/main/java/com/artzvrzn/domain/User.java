package com.artzvrzn.domain;

import static com.artzvrzn.domain.User.UNIQUE_CONSTRAINT_EMAIL;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_user",
       uniqueConstraints = @UniqueConstraint(name = UNIQUE_CONSTRAINT_EMAIL, columnNames = "email"))
@Getter
@Setter
public class User {
  public static final String UNIQUE_CONSTRAINT_EMAIL = "UC_EMAIL";
  @Id
  @GeneratedValue(generator = "UUID")
  @Setter(AccessLevel.PRIVATE)
  private UUID id;
  @Column(nullable = false)
  private String familyName;
  @Column(nullable = false)
  private String givenName;
  private String middleName;
  @Column(nullable = false)
  private String email;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;
}
