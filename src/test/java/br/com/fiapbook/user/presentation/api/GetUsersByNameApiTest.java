package br.com.fiapbook.user.presentation.api;

import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_NAME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiapbook.shared.annotation.DatabaseTest;
import br.com.fiapbook.shared.annotation.IntegrationTest;
import br.com.fiapbook.shared.api.JsonUtil;
import br.com.fiapbook.shared.api.PageUtil;
import br.com.fiapbook.shared.testData.user.UserTestData;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.presentation.dto.UserContent;
import br.com.fiapbook.user.presentation.dto.UserOutputDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetUsersByNameApiTest {

  private static final String URL_USERS = "/users/name/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  public GetUsersByNameApiTest(
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
    var userPage = PageUtil.generatePageOfUser(user);
    var userExpected = UserOutputDto.toPage(userPage);

    var request = get(URL_USERS + user.getName());
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var users = JsonUtil.fromJson(contentAsString, UserContent.class);
    assertThat(users.getContent()).usingRecursiveComparison().isEqualTo(userExpected);
  }

  @Test
  void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
    var request = get(URL_USERS + DEFAULT_USER_NAME);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.content", hasSize(0)));
  }
}
