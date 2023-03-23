package com.artzvrzn.service.impl;

import com.artzvrzn.dao.UserRepository;
import com.artzvrzn.domain.User;
import com.artzvrzn.dto.UserDto;
import com.artzvrzn.service.UserService;
import com.artzvrzn.service.ValidationService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
  private static final int DEFAULT_PAGE_SIZE = 10;
  private static final Sort DEFAULT_SORT = Sort.by("email").ascending();
  private final UserRepository userRepository;
  private final ConversionService converter;
  private final ValidationService<UserDto> validator;

  @Override
  public void createUser(UserDto dto) {
    validator.validate(dto);
    try {
      User user = converter.convert(dto, User.class);
      userRepository.save(user);
    } catch (Exception exc) {
      handleException(exc);
    }
  }

  @Override
  public Page<UserDto> getUsers(int page) {
    Page<User> users = userRepository.findAll(
      PageRequest.of(page - 1, DEFAULT_PAGE_SIZE, DEFAULT_SORT));
    return users.map(user -> converter.convert(user, UserDto.class));
  }

  @Override
  public List<UserDto> getUsers() {
    List<User> users = userRepository.findAll(DEFAULT_SORT);
    return users.stream().map(user -> converter.convert(user, UserDto.class))
      .collect(Collectors.toList());
  }

  private void handleException(Exception exc) {
    List<Throwable> throwables = ExceptionUtils.getThrowableList(exc);
    for (Throwable throwable: throwables) {
      if (throwable instanceof ConstraintViolationException) {
        String message = determineConstraintResponseMessage((ConstraintViolationException) throwable);
        throw new IllegalArgumentException(message);
      }
    }
    throw new IllegalStateException(exc);
  }

  private String determineConstraintResponseMessage(ConstraintViolationException exc) {
    String constraint = exc.getConstraintName();
    if (StringUtils.contains(constraint, User.UNIQUE_CONSTRAINT_EMAIL)) {
      return "user with such email already exists";
    } else {
      return "failed to create a new user, try again";
    }
  }
}
