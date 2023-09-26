package br.com.fiapbook.user.presentation.api;

import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiapbook.shared.annotation.DatabaseTest;
import br.com.fiapbook.shared.annotation.IntegrationTest;
import br.com.fiapbook.shared.api.JsonUtil;
import br.com.fiapbook.shared.testData.user.UserTestData;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.presentation.dto.UserOutputDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetUserByEmailApiTest {

  private static final String URL_USERS = "/users/email/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  public GetUserByEmailApiTest(
      MockMvc mockMvc,
      EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private User createUser() {
    var user = UserTestData.getUserWithoutId();
    return entityManager.merge(user);
  }

  @Test
  void shouldReturnUserWhenUserExists() throws Exception {
    var user = createUser();
    var userDtoExpected = UserOutputDto.from(user);

    var request = get(URL_USERS + user.getEmail());
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var userFound = JsonUtil.fromJson(contentAsString, User.class);
    var userDtoFound = UserOutputDto.from(userFound);
    assertThat(userDtoFound).usingRecursiveComparison().isEqualTo(userDtoExpected);
  }

  @Test
  void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
    var request = get(URL_USERS + DEFAULT_USER_EMAIL);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @ParameterizedTest
  @ValueSource(strings = {"email.domain.com", "@", "1234", "x@y.z"})
  void shouldReturnBadRequestWhenUserEmailIsInvalid(String email) throws Exception {
    var request = get(URL_USERS + email);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }
}
