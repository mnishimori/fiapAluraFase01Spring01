package br.com.fiapbook.user.model.usecase;

import br.com.fiapbook.shared.model.entity.validator.UuidValidator;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.model.messages.UserMessages;
import br.com.fiapbook.user.model.service.UserService;
import br.com.fiapbook.user.model.validator.UserEmailAlreadyRegisteredInOtherUserValidator;
import jakarta.persistence.NoResultException;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateUserUseCase {

  private final UserService userService;
  private final UuidValidator uuidValidator;
  private final UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator;

  public UpdateUserUseCase(
      UserService userService,
      UuidValidator uuidValidator,
      UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator) {
    this.userService = userService;
    this.uuidValidator = uuidValidator;
    this.userEmailAlreadyRegisteredInOtherUserValidator = userEmailAlreadyRegisteredInOtherUserValidator;
  }

  @Transactional
  public User execute(String userUuid, User userWithUpdatedAttributes) {
    uuidValidator.validate(userUuid);
    userEmailAlreadyRegisteredInOtherUserValidator.validate(userUuid, userWithUpdatedAttributes.getEmail());
    var userSaved = findUserBy(userUuid);
    var userToUpdate = updateAttibutesToUser(userSaved, userWithUpdatedAttributes);
    return userService.save(userToUpdate);
  }

  private User updateAttibutesToUser(User userSaved, User userToSave) {
    userSaved.setName(userToSave.getName());
    userSaved.setEmail(userToSave.getEmail());
    return userSaved;
  }

  private User findUserBy(String userUuid) {
    return userService.findById(UUID.fromString(userUuid))
        .orElseThrow(() -> new NoResultException(UserMessages.USER_ID_NOT_FOUND));
  }
}
