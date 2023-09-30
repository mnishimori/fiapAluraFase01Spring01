package br.com.fiapbook.shared.testData.user;

import br.com.fiapbook.user.model.entity.User;
import java.util.UUID;

public class UserTestData {
  public static final UUID DEFAULT_USER_UUID = UUID.randomUUID();
  public static final String DEFAULT_USER_UUID_FROM_STRING = DEFAULT_USER_UUID.toString();
  public static final String DEFAULT_USER_NAME = "Thomas Anderson";
  public static final String DEFAULT_USER_EMAIL = "thomas.anderson@itcompany.com";
  public static final String DEFAULT_USER_PASSWORD = "@Bcd1234";
  public static final String DEFAULT_USER_NAME_TO_UPDATE = "Neo";
  public static final String DEFAULT_USER_EMAIL_TO_UPDATE = "neo@matrix.com";

  public static final String USER_TEMPLATE_INPUT = """
      {"name": "%s", "email": "%s", "password": "%s"}""";

  public static final String USER_INPUT = USER_TEMPLATE_INPUT.formatted(
      DEFAULT_USER_NAME,
      DEFAULT_USER_EMAIL,
      DEFAULT_USER_PASSWORD);

  public static final String USER_TEMPLATE_UPDATE = """
      {"name": "%s", "email": "%s"}""";

  public static final String USER_UPDATE = USER_TEMPLATE_UPDATE.formatted(
      DEFAULT_USER_NAME_TO_UPDATE,
      DEFAULT_USER_EMAIL_TO_UPDATE);

  public static User createUser() {
    var uuid = UUID.randomUUID();
    return User.builder()
        .id(uuid)
        .email(DEFAULT_USER_EMAIL_TO_UPDATE)
        .name(DEFAULT_USER_NAME_TO_UPDATE)
        .password(DEFAULT_USER_PASSWORD)
        .build();
  }

  public static User createNewUser() {
    return User.builder()
        .email(DEFAULT_USER_EMAIL)
        .name(DEFAULT_USER_NAME)
        .password(DEFAULT_USER_PASSWORD)
        .build();
  }
}
