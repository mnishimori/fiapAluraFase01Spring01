package br.com.fiapbook.application.usecase.user;

import static br.com.fiapbook.shared.testData.user.UserTestData.getUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.fiapbook.application.user.usecase.GetAllUsersUseCase;
import br.com.fiapbook.domain.user.entity.User;
import br.com.fiapbook.domain.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUseCaseTest {

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 1;

  @Mock
  private UserService userService;
  @InjectMocks
  private GetAllUsersUseCase getAllUsersUseCase;

  @Test
  void shouldGetAllUsersWhenUsersExists() {
    var user = getUser();
    var users = List.of(user);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = users.size();
    var page = new PageImpl<>(users, pageable, size);

    when(userService.getAllUsersPaginated(pageable)).thenReturn(page);
    var usersFound = getAllUsersUseCase.execute(pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldReturnEmptyPageWhenDoesNotExistAnyUserSaved(){
    var users = new ArrayList<User>();
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = 0;
    var page = new PageImpl<>(users, pageable,  size);

    when(userService.getAllUsersPaginated(pageable)).thenReturn(page);
    var usersFound = getAllUsersUseCase.execute(pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }

}