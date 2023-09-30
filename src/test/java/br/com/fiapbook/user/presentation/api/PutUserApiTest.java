package br.com.fiapbook.user.presentation.api;

import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL_TO_UPDATE;
import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_NAME_TO_UPDATE;
import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static br.com.fiapbook.shared.testData.user.UserTestData.USER_TEMPLATE_UPDATE;
import static br.com.fiapbook.shared.testData.user.UserTestData.USER_UPDATE;
import static br.com.fiapbook.shared.testData.user.UserTestData.createNewUser;
import static br.com.fiapbook.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiapbook.shared.annotation.DatabaseTest;
import br.com.fiapbook.shared.annotation.IntegrationTest;
import br.com.fiapbook.user.model.entity.User;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PutUserApiTest {

  private static final String URL_USERS = "/users/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PutUserApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private User createAndPersistUser() {
    var user = createNewUser();
    return entityManager.merge(user);
  }

  private User createAndPersistUserWithDifferentAttributes() {
    var user = User.builder()
        .name(DEFAULT_USER_NAME_TO_UPDATE)
        .email(DEFAULT_USER_EMAIL_TO_UPDATE)
        .password(DEFAULT_USER_PASSWORD)
        .build();
    return entityManager.merge(user);
  }

  @Test
  void shouldUpdateUser() throws Exception {
    var user = createAndPersistUser();

    var request = put(URL_USERS + user.getId())
        .contentType(APPLICATION_JSON)
        .content(USER_UPDATE);
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isAccepted())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var userFound = entityManager.find(User.class, UUID.fromString(id));
    assertThat(userFound).isNotNull();
    assertThat(userFound.getName()).isEqualTo(DEFAULT_USER_NAME_TO_UPDATE);
    assertThat(userFound.getEmail()).isEqualTo(DEFAULT_USER_EMAIL_TO_UPDATE);
    assertThat(userFound.getPassword()).isEqualTo(DEFAULT_USER_PASSWORD);
  }

  @Test
  void shouldReturnNotFoundWhenUserWasNotFoundToUpdate() throws Exception {
    var userUuid = UUID.randomUUID();

    var request = put(URL_USERS + userUuid)
        .contentType(APPLICATION_JSON)
        .content(USER_UPDATE);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenUserUuidIsInvalid() throws Exception {
    var userUuid = "aaa";

    var request = put(URL_USERS + userUuid)
        .contentType(APPLICATION_JSON)
        .content(USER_UPDATE);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenUserEmailAlreadyExistsInOtherUser() throws Exception {
    var firstUser = createAndPersistUser();
    var secondUser = createAndPersistUserWithDifferentAttributes();

    var request = put(URL_USERS + secondUser.getId())
        .contentType(APPLICATION_JSON)
        .content(USER_TEMPLATE_UPDATE.formatted(firstUser.getName(), firstUser.getEmail()));
    mockMvc.perform(request)
        .andExpect(status().isConflict());
  }
}
