package br.com.fiapbook.application.user.usecase;

import br.com.fiapbook.domain.user.entity.User;
import br.com.fiapbook.domain.user.service.UserService;
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
