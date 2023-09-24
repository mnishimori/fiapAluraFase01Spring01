package br.com.fiapbook.user.model.validator;

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
      throw new ValidatorException("Password cannot be null or empty.");
    }
  }

  private void validateNumberInPassword(String password) {
    var numberCharInPassword = "(.*[0-9].*)";
    if (!password.matches(numberCharInPassword)) {
      throw new ValidatorException("Password must have at least one number character.");
    }
  }

  private void validateLowerCharacterInPassword(String password) {
    var lowerCharInPassword = "(.*[a-z].*)";
    if (!password.matches(lowerCharInPassword)) {
      throw new ValidatorException("Password must have at least one lower character.");
    }
  }
  private void validateUpperCharacterInPassword(String password) {
    var upperCharInPassword = "(.*[A-Z].*)";
    if (!password.matches(upperCharInPassword)) {
      throw new ValidatorException("Password must have at least one upper character.");
    }
  }

  private void validateSpecialCharacterInPassword(String password) {
    var specialCharInPassword = "(.*[@#$%^&+=].*)";
    if (!password.matches(specialCharInPassword)) {
      throw new ValidatorException("Password must have at least one special character @#$%^&+= .");
    }
  }

  private void validateNumberOfCharacterInPassword(String password) {
    var numberOfCharInPassword = "^(?=\\S+$).{8,20}$";
    if (!password.matches(numberOfCharInPassword)) {
      throw new ValidatorException("Password must have min eight and max twenty characters.");
    }
  }
}
