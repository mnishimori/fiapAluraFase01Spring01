package br.com.fiapbook.user.application.validator;

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
      throw new DuplicatedException("User already exists with email %s".formatted(email));
    }
  }
}
