package br.com.fiapbook.user.application.usecase;

import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.model.service.UserService;
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
