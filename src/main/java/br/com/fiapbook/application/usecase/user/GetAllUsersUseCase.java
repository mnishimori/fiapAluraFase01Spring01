package br.com.fiapbook.application.usecase.user;

import br.com.fiapbook.domain.entity.user.User;
import br.com.fiapbook.domain.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetAllUsersUseCase {

  private final UserService userService;

  public GetAllUsersUseCase(UserService userService) {
    this.userService = userService;
  }

  public Page<User> execute(Pageable pageable){
    return userService.getAllUsersPaginated(pageable);
  }
}
