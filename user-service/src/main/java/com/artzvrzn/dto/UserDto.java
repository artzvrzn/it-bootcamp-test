package com.artzvrzn.dto;

import com.artzvrzn.domain.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(SnakeCaseStrategy.class)
public class UserDto {
  @JsonProperty(access = Access.READ_ONLY)
  private UUID id;
  private String familyName;
  private String givenName;
  private String middleName;
  @JsonProperty(access = Access.READ_ONLY)
  private String fullName;
  private String email;
  private Role role;
}
