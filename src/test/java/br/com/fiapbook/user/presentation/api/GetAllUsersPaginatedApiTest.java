package br.com.fiapbook.user.presentation.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.fiapbook.shared.annotation.DatabaseTest;
import br.com.fiapbook.shared.annotation.IntegrationTest;
import br.com.fiapbook.shared.api.JsonUtil;
import br.com.fiapbook.shared.testData.user.UserTestData;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.presentation.dto.UserContent;
import br.com.fiapbook.user.presentation.dto.UserOutputDto;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTest
@DatabaseTest
class GetAllUsersPaginatedApiTest {

  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  public GetAllUsersPaginatedApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private static PageImpl<User> generatePageOfUser(User user) {
    var pageNumber = 0;
    var pageSize = 2;
    var totalItems = 3;
    var pageable = PageRequest.of(pageNumber, pageSize);
    return new PageImpl<>(List.of(user), pageable, totalItems);
  }

  private User createUser() {
    var user = UserTestData.getUserWithoutId();
    return entityManager.merge(user);
  }

  @Test
  void shouldReturnAllUsersWhenUsersExist() throws Exception {
    var user = createUser();
    var userPage = generatePageOfUser(user);
    var userExpected = UserOutputDto.toPage(userPage);

    var request = MockMvcRequestBuilders.get("/users");
    var mvcResult = mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var users = JsonUtil.fromJson(contentAsString, UserContent.class);
    assertThat(users.getContent()).usingRecursiveComparison().isEqualTo(userExpected);
  }
}
