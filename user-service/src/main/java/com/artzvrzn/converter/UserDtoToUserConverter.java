package com.artzvrzn.converter;

import com.artzvrzn.domain.User;
import com.artzvrzn.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoToUserConverter implements Converter<UserDto, User> {
  private final ModelMapper mapper;
  @Override
  public User convert(UserDto source) {
    User user = mapper.map(source, User.class);
    if (user.getMiddleName() == null) {
      user.setMiddleName("");
    }
    return user;
  }
}
