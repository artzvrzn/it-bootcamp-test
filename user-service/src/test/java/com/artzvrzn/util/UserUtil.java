package com.artzvrzn.util;

import com.artzvrzn.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import org.springframework.core.io.ClassPathResource;

public final class UserUtil {
  public static final ObjectMapper mapper = new ObjectMapper();

  private UserUtil() {}

  public static UserDto getUserDtoFromJson(String path) throws Exception {
    File jsonFile = new ClassPathResource(path).getFile();
    return mapper.readValue(jsonFile, UserDto.class);
  }
}
