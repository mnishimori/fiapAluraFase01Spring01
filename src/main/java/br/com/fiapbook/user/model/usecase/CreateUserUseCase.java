package br.com.fiapbook.user.model.usecase;

import br.com.fiapbook.user.model.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiapbook.user.model.validator.UserPasswordStrengthValidator;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.model.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateUserUseCase {

  private final UserService userService;
  private final UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;
  private final UserPasswordStrengthValidator userPasswordStrengthValidator;
  private final PasswordEncoder passwordEncoder;

  public CreateUserUseCase(
      UserService userService,
      UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator,
      UserPasswordStrengthValidator userPasswordStrengthValidator,
      PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.userEmailAlreadyRegisteredValidator = userEmailAlreadyRegisteredValidator;
    this.userPasswordStrengthValidator = userPasswordStrengthValidator;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User execute(User user) {
    userPasswordStrengthValidator.validate(user.getPassword());
    userEmailAlreadyRegisteredValidator.validate(user.getEmail());
    var passwordEncoded = passwordEncoder.encode(user.getPassword());
    user.setPassword(passwordEncoded);
    return userService.save(user);
  }
}
