package br.com.fiapbook.user.model.validator;

import static br.com.fiapbook.user.model.messages.UserMessages.USER_EMAIL_ALREADY_EXISTS;

import br.com.fiapbook.shared.exception.DuplicatedException;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.model.service.UserService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UserEmailAlreadyRegisteredInOtherUserValidator {

  private final UserService userService;

  public UserEmailAlreadyRegisteredInOtherUserValidator(UserService userService) {
    this.userService = userService;
  }

  public void validate(String userUuid, String email){
    var user = userService.findByEmail(email);
    if (user.isPresent() && emailAlreadyExistsInOtherUser(userUuid, user.get())) {
      throw new DuplicatedException(USER_EMAIL_ALREADY_EXISTS.formatted(email));
    }
  }

  private static boolean emailAlreadyExistsInOtherUser(String userUuid, User user) {
    return !user.getId().equals(UUID.fromString(userUuid));
  }
}
