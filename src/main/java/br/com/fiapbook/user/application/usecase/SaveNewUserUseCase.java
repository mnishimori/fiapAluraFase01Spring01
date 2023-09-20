package br.com.fiapbook.user.application.usecase;

import br.com.fiapbook.user.application.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.model.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SaveNewUserUseCase {

  private final UserService userService;
  private final UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;

  public SaveNewUserUseCase(
      UserService userService,
      UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator) {
    this.userService = userService;
    this.userEmailAlreadyRegisteredValidator = userEmailAlreadyRegisteredValidator;
  }

  @Transactional
  public User execute(User user){
    userEmailAlreadyRegisteredValidator.validate(user.getEmail());
    return userService.save(user);
  }
}
