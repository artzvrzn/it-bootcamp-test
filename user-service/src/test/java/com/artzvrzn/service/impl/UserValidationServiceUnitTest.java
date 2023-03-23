package com.artzvrzn.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import com.artzvrzn.dto.UserDto;
import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.service.ValidationService;
import com.artzvrzn.util.UserUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
class UserValidationServiceUnitTest {
  private static final String CORRECT_USER_JSON = "test/user-correct.json";
  private static final String INCORRECT_USER_JSON = "test/user-incorrect.json";

  @Test
  void validate_Success() throws Exception {
    ValidationService<UserDto> validationService = new UserValidationService();
    UserDto user = UserUtil.getUserDtoFromJson(CORRECT_USER_JSON);
    log.info(user);
    assertDoesNotThrow(() -> validationService.validate(user));
  }

  @Test
  void validate_Failed() throws Exception {
    ValidationService<UserDto> validationService = new UserValidationService();
    UserDto user = UserUtil.getUserDtoFromJson(INCORRECT_USER_JSON);
    log.info(user);
    assertThrowsExactly(ValidationException.class, () -> validationService.validate(user));
  }

  @Test
  void validationExceptionViolationsListLength() throws Exception {
    try {
      ValidationService<UserDto> validationService = new UserValidationService();
      validationService.validate(UserUtil.getUserDtoFromJson(CORRECT_USER_JSON));
    } catch (ValidationException exc) {
      assertEquals(5, exc.getViolations().size());
    }
  }
}