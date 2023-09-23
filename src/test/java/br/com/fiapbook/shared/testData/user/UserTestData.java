package br.com.fiapbook.shared.testData.user;

import br.com.fiapbook.user.model.entity.User;
import java.util.UUID;

public class UserTestData {
  public static final String DEFAULT_USER_NAME = "Thomas Anderson";
  public static final String DEFAULT_USER_EMAIL = "thomas.anderson@matrix.com";
  public static final String DEFAULT_USER_PASSWORD = "@Bcd1234";

  public static final String USER_TEMPLATE_INPUT = """
      {"name": "%s", "email": "%s", "password": "%s"}""";

  public static final String USER_INPUT = USER_TEMPLATE_INPUT.formatted(
      DEFAULT_USER_NAME,
      DEFAULT_USER_EMAIL,
      DEFAULT_USER_PASSWORD);

  public static User getUserWithId() {
    var uuid = UUID.randomUUID();
    return User.builder()
        .id(uuid)
        .email("email@domain.com")
        .name("Thomas Anderson")
        .password("@Bcd1234")
        .build();
  }

  public static User getUserWithoutId() {
    return User.builder()
        .email("email@domain.com")
        .name("Thomas Anderson")
        .password("@Bcd1234")
        .build();
  }
}
