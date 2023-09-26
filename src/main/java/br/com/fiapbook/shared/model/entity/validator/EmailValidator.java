package br.com.fiapbook.shared.model.entity.validator;

import br.com.fiapbook.shared.exception.ValidatorException;
import br.com.fiapbook.user.model.messages.UserMessages;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator {

  private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\."+
      "[a-zA-Z0-9_+&*-]+)*@" +
      "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
      "A-Z]{2,7}$";

  public void validate(String email) {
    if (email != null && !email.isBlank()) {
      var validEmail = Pattern.compile(EMAIL_REGEX).matcher(email).matches();
      if (!validEmail) {
        throw new ValidatorException(UserMessages.USER_EMAIL_INVALID.formatted(email));
      }
    }
  }
}
