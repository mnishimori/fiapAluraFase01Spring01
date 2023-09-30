package br.com.fiapbook.user.presentation.api;

import static br.com.fiapbook.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiapbook.shared.annotation.DatabaseTest;
import br.com.fiapbook.shared.annotation.IntegrationTest;
import br.com.fiapbook.shared.api.JsonUtil;
import br.com.fiapbook.shared.api.PageUtil;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.presentation.dto.UserContent;
import br.com.fiapbook.user.presentation.dto.UserOutputDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetUsersByNameOrEmailApiTest {

  private static final String URL_USERS = "/users/user-name-email";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  public GetUsersByNameOrEmailApiTest(
      MockMvc mockMvc,
      EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private User createAndSaveUser() {
    var user = createUser();
    return entityManager.merge(user);
  }

  @Test
  void shouldReturnUserWhenUserAlreadyExistsByName() throws Exception {
    var user = createAndSaveUser();
    var userPage = PageUtil.generatePageOfUser(user);
    var userOutputDtoExpected = UserOutputDto.toPage(userPage);

    var request = get(URL_USERS)
        .param("name", user.getName());
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var userFound = JsonUtil.fromJson(contentAsString, UserContent.class);
    assertThat(userFound.getContent()).usingRecursiveComparison().isEqualTo(userOutputDtoExpected);
  }

  @Test
  void shouldReturnUserWhenUserAlreadyExistsByEmail() throws Exception {
    var user = createAndSaveUser();
    var userPage = PageUtil.generatePageOfUser(user);
    var userOutputDtoExpected = UserOutputDto.toPage(userPage);

    var request = get(URL_USERS)
        .param("email", user.getEmail());
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var userFound = JsonUtil.fromJson(contentAsString, UserContent.class);
    assertThat(userFound.getContent()).usingRecursiveComparison().isEqualTo(userOutputDtoExpected);
  }

  @Test
  void shouldReturnUserWhenUserAlreadyExistsByNameAndEmail() throws Exception {
    var user = createAndSaveUser();
    var userPage = PageUtil.generatePageOfUser(user);
    var userOutputDtoExpected = UserOutputDto.toPage(userPage);

    var request = get(URL_USERS)
        .param("name", user.getName())
        .param("email", user.getEmail());
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var userFound = JsonUtil.fromJson(contentAsString, UserContent.class);
    assertThat(userFound.getContent()).usingRecursiveComparison().isEqualTo(userOutputDtoExpected);
  }
}
