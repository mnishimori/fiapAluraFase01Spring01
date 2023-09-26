package br.com.fiapbook.user.model.usecase;

import static br.com.fiapbook.user.model.messages.UserMessages.USER_EMAIL_NOT_FOUND;

import br.com.fiapbook.shared.model.entity.validator.EmailValidator;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.model.service.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Component;

@Component
public class GetUserByEmailUseCase {
  private final UserService userService;
  private final EmailValidator emailValidator;

  public GetUserByEmailUseCase(
      UserService userService,
      EmailValidator emailValidator) {
    this.userService = userService;
    this.emailValidator = emailValidator;
  }

  public User execute(String email) {
    emailValidator.validate(email);
    var userOptional = userService.findByEmail(email);
    return userOptional.orElseThrow(
        () -> new NoResultException(USER_EMAIL_NOT_FOUND.formatted(email)));
  }
}
