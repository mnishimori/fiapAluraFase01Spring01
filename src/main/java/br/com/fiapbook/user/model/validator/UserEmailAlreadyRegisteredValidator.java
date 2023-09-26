package br.com.fiapbook.user.model.validator;

import static br.com.fiapbook.user.model.messages.UserMessages.USER_EMAIL_ALREADY_EXISTS;

import br.com.fiapbook.shared.exception.DuplicatedException;
import br.com.fiapbook.user.model.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserEmailAlreadyRegisteredValidator {

  private final UserService userService;

  public UserEmailAlreadyRegisteredValidator(UserService userService) {
    this.userService = userService;
  }

  public void validate(String email){
    var user = userService.findByEmail(email);
    if (user.isPresent()) {
      throw new DuplicatedException(USER_EMAIL_ALREADY_EXISTS.formatted(email));
    }
  }
}
