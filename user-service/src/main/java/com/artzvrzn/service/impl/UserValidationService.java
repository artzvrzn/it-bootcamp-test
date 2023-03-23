package com.artzvrzn.service.impl;

import com.artzvrzn.domain.Role;
import com.artzvrzn.domain.Violation;
import com.artzvrzn.dto.UserDto;
import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.service.ValidationService;
import com.artzvrzn.util.LogMessages;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserValidationService implements ValidationService<UserDto> {
  private final static String GIVEN_NAME = "given_name";
  private final static String FAMILY_NAME = "family_name";
  private final static String MIDDLE_NAME = "middle_name";
  private final static String EMAIL = "email";
  private final static String ROLE = "role";

  private final static String ERROR_BLANK_FIELD = "field cannot be blank";
  private final static String ERROR_LENGTH_GREATER = "length must be less than %s symbols";
  private final static String ERROR_NON_LATIN = "all characters must be latin";
  private final static String ERROR_INVALID_EMAIL = "invalid email";
  private final static String ERROR_INVALID_ROLE = "invalid role";

  @Override
  public void validate(UserDto target) {
    Violation givenNameViolation = validateGivenName(target.getGivenName());
    Violation familyNameViolation = validateFamilyName(target.getFamilyName());
    Violation middleNameViolation = validateMiddleName(target.getMiddleName());
    Violation emailViolation = validateEmail(target.getEmail());
    Violation roleViolation = validateRole(target.getRole());
    List<Violation> violations = Stream.of(
      givenNameViolation, familyNameViolation, middleNameViolation, emailViolation, roleViolation)
      .filter(Objects::nonNull)
      .collect(Collectors.toList());
    log.info(LogMessages.REQUEST_BODY_ERROR + ": " + violations, violations.size());
    if (!violations.isEmpty()) {
      throw new ValidationException(violations);
    }
  }

  private Violation validateGivenName(String givenName) {
    return validateStringField(GIVEN_NAME, givenName, 20);
  }

  private Violation validateFamilyName(String familyName) {
    return validateStringField(FAMILY_NAME, familyName, 40);
  }

  private Violation validateMiddleName(String middleName) {
    if (StringUtils.isBlank(middleName)) {
      return null;
    }
    return validateStringField(MIDDLE_NAME, middleName, 40);
  }

  private Violation validateStringField(String field, String value, int maxLength) {
    if (StringUtils.isBlank(value)) {
      return new Violation(field, ERROR_BLANK_FIELD);
    }
    if (value.length() > maxLength) {
      return new Violation(field, String.format(ERROR_LENGTH_GREATER, maxLength));
    }
    if (!isLatin(value)) {
      return new Violation(field, ERROR_NON_LATIN);
    }
    return null;
  }

  private Violation validateEmail(String email) {
    final int maxLength = 50;
    if (StringUtils.isBlank(email)) {
      return new Violation(EMAIL, ERROR_BLANK_FIELD);
    }
    if (email.length() > maxLength) {
      return new Violation(email, String.format(ERROR_LENGTH_GREATER, maxLength));
    }
    boolean isEmail = EmailValidator.getInstance().isValid(email);
    return isEmail ? null : new Violation(EMAIL, ERROR_INVALID_EMAIL);
  }

  private Violation validateRole(Role role) {
    if (role == null) {
      return new Violation(ROLE, ERROR_BLANK_FIELD);
    }
    if (Role.INVALID.equals(role)) {
      return new Violation(ROLE, ERROR_INVALID_ROLE);
    }
    return null;
  }

  private boolean isLatin(String target) {
    return target.codePoints().allMatch(ch -> ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z');
  }
}
