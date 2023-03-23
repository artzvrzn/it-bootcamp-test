package com.artzvrzn.controller.rest;

import com.artzvrzn.dto.UserDto;
import com.artzvrzn.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UserController {
  private final UserService userService;

  @PostMapping(value = {"/user", "/user/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createUser(@RequestBody UserDto dto) {
    userService.createUser(dto);
  }

  @GetMapping(value = {"/users", "/users/"},
    produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<UserDto> getUsers() {
    return userService.getUsers();
  }

  @GetMapping(value = {"/users/{page}", "/users{page}/"},
              produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Page<UserDto> getUsers(@PathVariable("page") int page) {
    return userService.getUsers(page);
  }
}
