package br.com.fiapbook.user.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiapbook.shared.testData.user.UserTestData;
import br.com.fiapbook.user.application.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiapbook.user.model.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

  @Mock
  private UserService userService;
  @Mock
  private UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;
  @InjectMocks
  private CreateUserUseCase createUserUseCase;

  @Test
  void shouldCreateNewUserWhenAllUserAttributesAreCorrect(){
    var user = UserTestData.getUserWithoutId();
    when(userService.save(user)).then(returnsFirstArg());

    var userSaved = createUserUseCase.execute(user);

    verify(userEmailAlreadyRegisteredValidator).validate(user.getEmail());
    verify(userService).save(user);
    assertThat(userSaved).isNotNull();
    assertThat(userSaved.getName()).isEqualTo(user.getName());
    assertThat(userSaved.getEmail()).isEqualTo(user.getEmail());
  }

}