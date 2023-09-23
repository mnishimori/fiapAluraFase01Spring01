package br.com.fiapbook.user.application.usecase;

import br.com.fiapbook.user.application.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiapbook.user.application.validator.UserPasswordStrengthValidator;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.model.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateUserUseCase {

  private final UserService userService;
  private final UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;
  private final UserPasswordStrengthValidator userPasswordStrengthValidator;

  public CreateUserUseCase(
      UserService userService,
      UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator,
      UserPasswordStrengthValidator userPasswordStrengthValidator) {
    this.userService = userService;
    this.userEmailAlreadyRegisteredValidator = userEmailAlreadyRegisteredValidator;
    this.userPasswordStrengthValidator = userPasswordStrengthValidator;
  }

  @Transactional
  public User execute(User user) {
    userPasswordStrengthValidator.validate(user.getPassword());
    userEmailAlreadyRegisteredValidator.validate(user.getEmail());
    return userService.save(user);
  }
}
