package br.com.fiapbook.user.presentation.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContent {

  private List<UserOutputDto> content;

}
