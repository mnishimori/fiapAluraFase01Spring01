package br.com.fiapbook.application.usecase.user;

import br.com.fiapbook.domain.entity.user.User;
import br.com.fiapbook.domain.service.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SaveNewUserUseCase {

  private final UserService userService;

  public SaveNewUserUseCase(UserService userService) {
    this.userService = userService;
  }

  @Transactional
  public User execute(User user){
    return userService.save(user);
  }
}
