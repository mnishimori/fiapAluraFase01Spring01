package br.com.fiapbook.user.application.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiapbook.shared.exception.ValidatorException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserPasswordStrengthValidatorTest {

  @Spy
  private UserPasswordStrengthValidator userPasswordStrengthValidator;

  @ParameterizedTest
  @ValueSource(strings = {"@Bcd1234"})
  void shouldValidatePassword(String password) {
    assertDoesNotThrow(() -> userPasswordStrengthValidator.validate(password));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenPasswordIsNullOrEmpty(String password) {
    assertThrows(ValidatorException.class, () -> userPasswordStrengthValidator.validate(password));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "a", "@", "A", "@Bc1", "?=.*[@#$%^&-+=()]",
      "@Bcd12345678901234567", "@bcd1234", "Abcd1234"})
  void shouldThrowExceptionWhenPasswordIsInvalid(String password) {
    assertThrows(ValidatorException.class, () -> userPasswordStrengthValidator.validate(password));
  }
}