package com.artzvrzn.controller.rest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.artzvrzn.dto.UserDto;
import com.artzvrzn.service.UserService;
import com.artzvrzn.util.UserUtil;
import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntTest {
  private static final String UUID4_REGEX =
    "[a-f0-9]{8}-?[a-f0-9]{4}-?4[a-f0-9]{3}-?[89ab][a-f0-9]{3}-?[a-f0-9]{12}";
  @Autowired
  private UserService userService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  @SqlGroup({
    @Sql(scripts = "classpath:/test/reset-table.sql", executionPhase = BEFORE_TEST_METHOD),
    @Sql(scripts = "classpath:/test/init-data.sql", executionPhase = BEFORE_TEST_METHOD)
  })
  void testCreateUser_Success() throws Exception {
    File jsonFile = new ClassPathResource("test/user-correct.json").getFile();
    String userJson = Files.readString(jsonFile.toPath());
    mockMvc.perform(
      post("/user")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(userJson))
      .andDo(print())
      .andExpect(status().isCreated());
    assertThat(userService.getUsers()).hasSize(13);
  }

  @Test
  @Sql(scripts = "classpath:/test/reset-table.sql", executionPhase = BEFORE_TEST_METHOD)
  void testCreateUserWithSameEmail_Failed() throws Exception {
    UserDto user = UserUtil.getUserDtoFromJson("test/user-correct.json");
    userService.createUser(user);
    assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
  }

  @Test
  @SqlGroup({
    @Sql(scripts = "classpath:/test/reset-table.sql", executionPhase = BEFORE_TEST_METHOD),
    @Sql(scripts = "classpath:/test/init-data.sql", executionPhase = BEFORE_TEST_METHOD)
  })
  void testCreateUserWithLongFamilyName_Failed() throws Exception {
    UserDto user = UserUtil.getUserDtoFromJson("test/user-correct.json");
    user.setFamilyName("LOOOOOOOOOOOOOOOOONG FAMIIIIIIIILYYYYYY NAAAAAMEEEEEE"); //> 40
    mockMvc.perform(
        post("/user")
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(UserUtil.mapper.writeValueAsString(user)))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  @Test
  @SqlGroup({
    @Sql(scripts = "classpath:/test/reset-table.sql", executionPhase = BEFORE_TEST_METHOD),
    @Sql(scripts = "classpath:/test/init-data.sql", executionPhase = BEFORE_TEST_METHOD)
  })
  void testCreateUser_Failed() throws Exception {
    File jsonFile = new ClassPathResource("test/user-incorrect.json").getFile();
    String userJson = Files.readString(jsonFile.toPath());
    mockMvc.perform(
        post("/user")
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(userJson))
      .andDo(print())
      .andExpect(status().isBadRequest());
    assertThat(userService.getUsers()).hasSize(12);
  }

  @Test
  @SqlGroup({
    @Sql(scripts = "classpath:/test/reset-table.sql", executionPhase = BEFORE_TEST_METHOD),
    @Sql(scripts = "classpath:/test/init-data.sql", executionPhase = BEFORE_TEST_METHOD)
  })
  void testGetAllUsers() throws Exception {
    mockMvc.perform(get("/users"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", hasSize(12)))
      .andExpect(jsonPath("$.[0].id", matchesPattern(UUID4_REGEX)))
      .andExpect(jsonPath("$.[11].id", matchesPattern(UUID4_REGEX)));
  }

  @Test
  @SqlGroup({
    @Sql(scripts = "classpath:/test/reset-table.sql", executionPhase = BEFORE_TEST_METHOD),
    @Sql(scripts = "classpath:/test/init-data.sql", executionPhase = BEFORE_TEST_METHOD)
  })
  void testGetUsersFirstPage() throws Exception {
    mockMvc.perform(get("/users/1"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$").isMap())
      .andExpect(jsonPath("$.number", is(1)))
      .andExpect(jsonPath("$.size", is(10)))
      .andExpect(jsonPath("$.total_pages", is(2)))
      .andExpect(jsonPath("$.total_elements", is(12)))
      .andExpect(jsonPath("$.first", is(true)))
      .andExpect(jsonPath("$.last", is(false)))
      .andExpect(jsonPath("$.content").isArray())
      .andExpect(jsonPath("$.content", hasSize(10)));
  }

  @Test
  @SqlGroup({
    @Sql(scripts = "classpath:/test/reset-table.sql", executionPhase = BEFORE_TEST_METHOD),
    @Sql(scripts = "classpath:/test/init-data.sql", executionPhase = BEFORE_TEST_METHOD)
  })
  void testGetUsersSecondPage() throws Exception {
    mockMvc.perform(get("/users/2"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$").isMap())
      .andExpect(jsonPath("$.number", is(2)))
      .andExpect(jsonPath("$.size", is(10)))
      .andExpect(jsonPath("$.total_pages", is(2)))
      .andExpect(jsonPath("$.total_elements", is(12)))
      .andExpect(jsonPath("$.first", is(false)))
      .andExpect(jsonPath("$.last", is(true)))
      .andExpect(jsonPath("$.content").isArray())
      .andExpect(jsonPath("$.content", hasSize(2)));
  }
}