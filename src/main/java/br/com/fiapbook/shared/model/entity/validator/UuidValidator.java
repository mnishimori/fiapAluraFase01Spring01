package br.com.fiapbook.shared.model.entity.validator;

import static br.com.fiapbook.shared.model.messages.SharedMessages.UUID_INVALID;

import br.com.fiapbook.shared.exception.ValidatorException;
import br.com.fiapbook.shared.util.IsUUID;
import org.springframework.stereotype.Component;

@Component
public class UuidValidator {

  public void validate(String uuid) {
    if (!IsUUID.isUUID().matches(uuid)) {
      throw new ValidatorException(UUID_INVALID.formatted(uuid));
    }
  }
}
