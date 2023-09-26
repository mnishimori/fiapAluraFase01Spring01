package br.com.fiapbook.user.model.validator;

import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_CANNOT_BE_NULL_OR_EMPTY;
import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CHAR;
import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER_CHAR;
import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHAR;
import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPER_CHAR;
import static br.com.fiapbook.user.model.messages.UserMessages.USER_PASSWORD_MUST_HAVE_MIN_EIGHT_CHAR_AND_MAX_TWENTY_CHAR;

import br.com.fiapbook.shared.exception.ValidatorException;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordStrengthValidator {

  public UserPasswordStrengthValidator() {
  }

  public void validate(String password) {
    validateNullOrEmptyPassword(password);
    validateNumberInPassword(password);
    validateLowerCharacterInPassword(password);
    validateUpperCharacterInPassword(password);
    validateSpecialCharacterInPassword(password);
    validateNumberOfCharacterInPassword(password);
  }

  private void validateNullOrEmptyPassword(String password) {
    if (password == null || password.isBlank()) {
      throw new ValidatorException(USER_PASSWORD_CANNOT_BE_NULL_OR_EMPTY);
    }
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

  private void validateNumberOfCharacterInPassword(String password) {
    var numberOfCharInPassword = "^(?=\\S+$).{8,20}$";
    if (!password.matches(numberOfCharInPassword)) {
      throw new ValidatorException(USER_PASSWORD_MUST_HAVE_MIN_EIGHT_CHAR_AND_MAX_TWENTY_CHAR);
    }
  }
}
