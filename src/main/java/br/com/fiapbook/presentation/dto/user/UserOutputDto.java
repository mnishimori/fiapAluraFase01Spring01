package br.com.fiapbook.presentation.dto.user;

import br.com.fiapbook.domain.entity.user.User;
import org.springframework.data.domain.Page;

public record UserOutputDto(
    String id,
    String name,
    String email
) {

  public UserOutputDto(User user) {
    this(user.getId().toString(),
        user.getName(),
        user.getEmail());
  }

  public static Page<UserOutputDto> toPage(Page<User> usersPage) {
    return usersPage.map(UserOutputDto::new);
  }

}
