package br.com.fiapbook.shared.testData.user;

import br.com.fiapbook.domain.user.entity.User;
import java.util.UUID;

public class UserTestData {

  public static User getUser() {
    var uuid = UUID.randomUUID();
    return User.builder()
        .id(uuid)
        .email("email@domain.com")
        .name("Thomas Anderson")
        .password("123456")
        .build();
  }

}
