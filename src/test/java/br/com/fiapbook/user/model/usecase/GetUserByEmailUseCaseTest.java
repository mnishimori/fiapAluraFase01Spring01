package br.com.fiapbook.user.model.usecase;

import static br.com.fiapbook.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static br.com.fiapbook.shared.testData.user.UserTestData.createUser;
import static br.com.fiapbook.user.model.messages.UserMessages.USER_EMAIL_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiapbook.shared.model.entity.validator.EmailValidator;
import br.com.fiapbook.user.model.service.UserService;
import jakarta.persistence.NoResultException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetUserByEmailUseCaseTest {

  @Mock
  private UserService userService;
  @Mock
  private EmailValidator emailValidator;
  @InjectMocks
  private GetUserByEmailUseCase getUserByEmailUseCase;

  @Test
  void shouldGetUserByEmailWhenUserExists() {
    var user = createUser();
    var userEmail = user.getEmail();
    when(userService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    var userFound = getUserByEmailUseCase.execute(userEmail);

    assertThat(userFound).isNotNull();
    assertThat(userFound).isSameAs(user);

    verify(emailValidator).validate(userEmail);
  }

  @Test
  void shouldThrowExceptionWhenUserDoesNotExist() {
    var userEmail = DEFAULT_USER_EMAIL;
    when(userService.findByEmail(userEmail)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> getUserByEmailUseCase.execute(userEmail))
        .isInstanceOf(NoResultException.class)
        .hasMessage(USER_EMAIL_NOT_FOUND.formatted(userEmail));

    verify(emailValidator).validate(userEmail);
  }
}
