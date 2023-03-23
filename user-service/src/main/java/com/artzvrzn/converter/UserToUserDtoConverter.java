package com.artzvrzn.converter;

import com.artzvrzn.domain.User;
import com.artzvrzn.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class UserToUserDtoConverter implements Converter<User, UserDto> {
  private final ModelMapper mapper;

  @Override
  public UserDto convert(User source) {
    UserDto dto = mapper.map(source, UserDto.class);
    dto.setFullName(buildFullName(dto));
    return dto;
  }

  private static String buildFullName(UserDto dto) {
    String familyName = dto.getFamilyName();
    String middleName = dto.getMiddleName();
    String givenName = dto.getGivenName();
    StringBuilder sb = new StringBuilder();
    if (!familyName.isBlank()) {
      sb.append(StringUtils.capitalize(familyName));
    }
    if (!givenName.isBlank()) {
      sb.append(" ").append(StringUtils.capitalize(givenName));
    }
    if (!middleName.isBlank()) {
      sb.append(" ").append(StringUtils.capitalize(middleName));
    }
    return sb.toString();
  }
}
