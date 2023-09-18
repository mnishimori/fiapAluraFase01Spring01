package br.com.fiapbook.user.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiapbook.shared.testData.user.UserTestData;
import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.model.repository.UserRepository;
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
class UserServiceTest {

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 1;

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserService userService;

  @Test
  void shouldSaveUserWhenAllUserAttributesAreCorrect() {
      var user = UserTestData.getUserWithoutId();
      when(userRepository.save(user)).then(returnsFirstArg());

      var userSaved = userService.save(user);

      assertThat(userSaved).isNotNull();
      assertThat(userSaved.getName()).isEqualTo(user.getName());
      assertThat(userSaved.getEmail()).isEqualTo(user.getEmail());
      verify(userRepository).save(user);
  }

  @Test
  void shouldGetAllUsersPaginatedWhenUsersExits() {
    var user = UserTestData.getUserWithId();
    var users = List.of(user);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = users.size();
    var page = new PageImpl<>(users, pageable, size);

    when(userRepository.findAll(pageable)).thenReturn(page);
    var usersFound = userService.getAllUsersPaginated(pageable);

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

    when(userRepository.findAll(pageable)).thenReturn(page);
    var usersFound = userService.getAllUsersPaginated(pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }

}