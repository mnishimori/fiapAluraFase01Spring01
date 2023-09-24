package br.com.fiapbook.user.application.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiapbook.shared.exception.DuplicatedException;
import br.com.fiapbook.shared.testData.user.UserTestData;
import br.com.fiapbook.user.model.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserEmailAlreadyRegisteredValidatorTest {

  @Mock
  private UserService userService;
  @InjectMocks
  private UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;

  @Test
  void shouldValidateWhenUserEmailDoesNotExist() {
    var user = UserTestData.getUserWithoutId();
    Mockito.when(userService.findByEmail(user.getEmail())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userEmailAlreadyRegisteredValidator.validate(user.getEmail()));
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyExists() {
    var user = UserTestData.getUserWithId();
    Mockito.when(userService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    assertThrows(DuplicatedException.class,
        () -> userEmailAlreadyRegisteredValidator.validate(user.getEmail()));
  }
}
