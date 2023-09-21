package br.com.fiapbook.shared.testData.user;

import br.com.fiapbook.user.model.entity.User;
import java.util.UUID;

public class UserTestData {

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
