package br.com.fiapbook.user.model.validator;

import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CHAR;
import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER_CHAR;
import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHAR;
import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPER_CHAR;

import br.com.fiapbook.shared.exception.ValidatorException;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordStrengthValidator {

  public UserPasswordStrengthValidator() {
  }

  public void validate(String password) {
    validateNumberInPassword(password);
    validateLowerCharacterInPassword(password);
    validateUpperCharacterInPassword(password);
    validateSpecialCharacterInPassword(password);
  }

  private void validateNumberInPassword(String password) {
    var numberCharInPassword = "(.*[0-9].*)";
    if (!password.matches(numberCharInPassword)) {
      throw new ValidatorException(USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER_CHAR);
    }
  }

  private void validateLowerCharacterInPassword(String password) {
    var lowerCharInPassword = "(.*[a-z].*)";
    if (!password.matches(lowerCharInPassword)) {
      throw new ValidatorException(USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CHAR);
    }
  }
  private void validateUpperCharacterInPassword(String password) {
    var upperCharInPassword = "(.*[A-Z].*)";
    if (!password.matches(upperCharInPassword)) {
      throw new ValidatorException(USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPER_CHAR);
    }
  }

  private void validateSpecialCharacterInPassword(String password) {
    var specialCharInPassword = "(.*[@#$%^&+=].*)";
    if (!password.matches(specialCharInPassword)) {
      throw new ValidatorException(USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHAR);
    }
  }
}
