package br.com.fiapbook.user.presentation.dto;

import br.com.fiapbook.user.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PutUserInputDto(
    @NotBlank(message = "Name is required.")
    @Length(max = 500, message = "Max name length is 500 characters.")
    String name,
    @NotBlank(message = "email is required.")
    @Length(max = 500, message = "Max email length is 500 characters.")
    @Email
    String email
) {

  public static User toUser(PutUserInputDto putUserInputDto) {
    return User.builder()
        .name(putUserInputDto.name)
        .email(putUserInputDto.email)
        .build();
  }
}
