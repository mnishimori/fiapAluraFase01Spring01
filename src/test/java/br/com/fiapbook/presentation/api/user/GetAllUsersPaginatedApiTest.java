package br.com.fiapbook.presentation.api.user;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.fiapbook.domain.user.entity.User;
import br.com.fiapbook.shared.annotation.DatabaseTest;
import br.com.fiapbook.shared.annotation.IntegrationTest;
import br.com.fiapbook.shared.testData.user.UserTestData;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

  private User createUser() {
    var user = UserTestData.getUserWithoutId();
    return entityManager.merge(user);
  }

  @Test
  void shouldReturnAllUsersWhenUsersExist() throws Exception {
    var user = createUser();

    var request = MockMvcRequestBuilders.get("/users");
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.content", hasSize(1)));
  }

}
