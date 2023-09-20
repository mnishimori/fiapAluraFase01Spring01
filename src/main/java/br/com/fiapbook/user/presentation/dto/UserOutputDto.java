package br.com.fiapbook.user.presentation.dto;

import br.com.fiapbook.user.model.entity.User;
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

  public static UserOutputDto from(User user){
    return new UserOutputDto(user);
  }

}
