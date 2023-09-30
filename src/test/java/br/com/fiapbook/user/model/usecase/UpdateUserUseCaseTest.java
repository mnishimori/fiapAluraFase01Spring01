package br.com.fiapbook.user.model.usecase;

import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_NAME;
import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static br.com.fiapbook.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiapbook.shared.model.entity.validator.UuidValidator;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.model.service.UserService;
import br.com.fiapbook.user.model.validator.UserEmailAlreadyRegisteredInOtherUserValidator;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

  @Mock
  private UserService userService;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator;
  @InjectMocks
  private UpdateUserUseCase updateUserUseCase;

  @Test
  void shouldUpdateUser() {
    var userFound = createUser();
    var userToUpdate = User.builder()
        .id(userFound.getId())
        .name(DEFAULT_USER_NAME)
        .email(DEFAULT_USER_EMAIL)
        .password(DEFAULT_USER_PASSWORD)
        .build();
    when(userService.findById(userFound.getId())).thenReturn(Optional.of(userFound));
    when(userService.save(any())).thenReturn(userToUpdate);

    var userUpdated = updateUserUseCase.execute(userFound.getId().toString(), userToUpdate);

    assertThat(userUpdated).isNotNull();
    assertThat(userUpdated).usingRecursiveComparison().isEqualTo(userToUpdate);
    Mockito.verify(uuidValidator).validate(userToUpdate.getId().toString());
    Mockito.verify(userEmailAlreadyRegisteredInOtherUserValidator)
        .validate(userToUpdate.getId().toString(), userToUpdate.getEmail());
  }
}
