package br.com.fiapbook.user.presentation.dto;

import br.com.fiapbook.user.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserInputDto(
    @NotBlank(message = "Name is required!")
    @Length(max = 500, message = "Max name length is 500 characters")
    String name,
    @NotBlank(message = "email is required!")
    @Length(max = 500, message = "Max email length is 500 characters")
    @Email
    String email,
    @NotBlank(message = "Password is required!")
    @Length(min = 6, max = 255, message = "Min password length is 6 characters and max password length is 255 characters")
    String password
) {

  public static User toUser(UserInputDto userInputDto) {
    return User.builder()
        .name(userInputDto.name)
        .email(userInputDto.email)
        .password(userInputDto.password)
        .build();
  }

}
