package br.com.fiapbook.user.presentation.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserContent {

  private List<UserOutputDto> content;

}
