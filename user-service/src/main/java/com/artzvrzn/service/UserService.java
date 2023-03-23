package com.artzvrzn.service;

import com.artzvrzn.dto.UserDto;
import java.util.List;
import org.springframework.data.domain.Page;

public interface UserService {

  void createUser(UserDto dto);

  Page<UserDto> getUsers(int page);

  List<UserDto> getUsers();
}
