package br.com.fiapbook.user.presentation.api;

import br.com.fiapbook.shared.annotation.DatabaseTest;
import br.com.fiapbook.shared.annotation.IntegrationTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class CreateUserApiTest {

  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  public CreateUserApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  @Test
  void shouldCreateUser(){

  }

}
