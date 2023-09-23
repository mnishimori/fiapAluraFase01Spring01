package br.com.fiapbook.user.presentation.api;

import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_NAME;
import static br.com.fiapbook.shared.testData.user.UserTestData.USER_INPUT;
import static br.com.fiapbook.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class CreateUserApiTest {

  private static final String URL_USERS = "/users";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  public CreateUserApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  @Test
  void shouldCreateUser() throws Exception {
    var request = post(URL_USERS)
        .contentType(APPLICATION_JSON)
        .content(USER_INPUT);
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var userFound = entityManager.find(User.class, UUID.fromString(id));
    assertThat(userFound).isNotNull();
    assertThat(userFound.getName()).isEqualTo(DEFAULT_USER_NAME);
    assertThat(userFound.getEmail()).isEqualTo(DEFAULT_USER_EMAIL);
  }
}
