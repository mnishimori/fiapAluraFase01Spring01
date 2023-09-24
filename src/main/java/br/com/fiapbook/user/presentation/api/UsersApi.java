package br.com.fiapbook.user.presentation.api;

import br.com.fiapbook.user.model.usecase.GetAllUsersUseCase;
import br.com.fiapbook.user.model.usecase.CreateUserUseCase;
import br.com.fiapbook.user.presentation.dto.UserInputDto;
import br.com.fiapbook.user.presentation.dto.UserOutputDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersApi {

  private final CreateUserUseCase createUserUseCase;
  private final GetAllUsersUseCase getAllUsersUseCase;

  public UsersApi(
      CreateUserUseCase createUserUseCase,
      GetAllUsersUseCase getAllUsersUseCase) {
    this.createUserUseCase = createUserUseCase;
    this.getAllUsersUseCase = getAllUsersUseCase;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<UserOutputDto> getAllUsersPaginated(
      @PageableDefault(size = 10, page = 0, sort = {"name"}) Pageable pageable) {
    var usersPage = getAllUsersUseCase.execute(pageable);
    return UserOutputDto.toPage(usersPage);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserOutputDto createUser(@RequestBody @Valid UserInputDto userInputDto) {
    var user = UserInputDto.toUser(userInputDto);
    var userCreated = createUserUseCase.execute(user);
    return UserOutputDto.from(userCreated);
  }
}
