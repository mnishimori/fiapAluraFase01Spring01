package br.com.fiapbook.user.model.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiapbook.shared.exception.DuplicatedException;
import br.com.fiapbook.shared.exception.ValidatorException;
import br.com.fiapbook.shared.testData.user.UserTestData;
import br.com.fiapbook.user.model.service.UserService;
import br.com.fiapbook.user.model.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiapbook.user.model.validator.UserPasswordStrengthValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

  @Mock
  private UserService userService;
  @Mock
  private UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;
  @Spy
  private UserPasswordStrengthValidator userPasswordStrengthValidator;
  @InjectMocks
  private CreateUserUseCase createUserUseCase;

  @Test
  void shouldCreateNewUserWhenAllUserAttributesAreCorrect() {
    var user = UserTestData.getUserWithoutId();
    var originalPassword = user.getPassword();
    when(userService.save(user)).then(returnsFirstArg());

    var userSaved = createUserUseCase.execute(user);

    verify(userEmailAlreadyRegisteredValidator).validate(user.getEmail());
    verify(userPasswordStrengthValidator).validate(originalPassword);
    verify(userService).save(user);
    assertThat(userSaved).isNotNull();
    assertThat(userSaved.getName()).isEqualTo(user.getName());
    assertThat(userSaved.getEmail()).isEqualTo(user.getEmail());
  }

  @Test
  void shouldThrowExceptionWhenUserPasswordIsInvalid() {
    var user = UserTestData.getUserWithoutId();
    user.setPassword("123456");

    assertThatThrownBy(() -> createUserUseCase.execute(user)).isInstanceOf(
        ValidatorException.class);

    verify(userPasswordStrengthValidator).validate(user.getPassword());
    verify(userEmailAlreadyRegisteredValidator, never()).validate(user.getEmail());
    verify(userService, never()).save(user);
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyExists() {
    var user = UserTestData.getUserWithoutId();
    doThrow(DuplicatedException.class).when(userEmailAlreadyRegisteredValidator)
        .validate(user.getEmail());

    assertThatThrownBy(() -> createUserUseCase.execute(user)).isInstanceOf(
        DuplicatedException.class);

    verify(userPasswordStrengthValidator).validate(user.getPassword());
    verify(userEmailAlreadyRegisteredValidator).validate(user.getEmail());
    verify(userService, never()).save(user);
  }
}