package br.com.fiapbook.user.model.service;

import static br.com.fiapbook.shared.testData.user.UserTestData.*;
import static br.com.fiapbook.shared.testData.user.UserTestData.getUserWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiapbook.user.infrastructure.repository.UserRepository;
import br.com.fiapbook.user.model.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    var user = getUserWithoutId();
    when(userRepository.save(user)).then(returnsFirstArg());

    var userSaved = userService.save(user);

    assertThat(userSaved).isNotNull();
    assertThat(userSaved.getName()).isEqualTo(user.getName());
    assertThat(userSaved.getEmail()).isEqualTo(user.getEmail());
    verify(userRepository).save(user);
  }

  @Test
  void shouldGetAllUsersPaginatedWhenUsersExits() {
    var user = getUserWithId();
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
  void shouldReturnEmptyPageWhenDoesNotExistAnyUserSaved() {
    var users = new ArrayList<User>();
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = 0;
    var page = new PageImpl<>(users, pageable, size);
    when(userRepository.findAll(pageable)).thenReturn(page);

    var usersFound = userService.getAllUsersPaginated(pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldFindUserByEmailWhenUserExists() {
    var user = getUserWithId();
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    var userFoundOptional = userService.findByEmail(user.getEmail());
    var userFound = userFoundOptional.orElse(null);

    assertThat(userFound).isNotNull();
    assertThat(userFound.getName()).isEqualTo(user.getName());
    assertThat(userFound.getEmail()).isEqualTo(user.getEmail());
  }

  @Test
  void shouldReturnEmptyWhenFindUserByEmailDoesNotFound() {
    var user = getUserWithoutId();
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

    var userFoundOptional = userService.findByEmail(user.getEmail());
    var userFound = userFoundOptional.orElse(null);

    assertThat(userFound).isNull();
  }

  @Test
  void shouldFindUserByNameWhenUserExists() {
    var user = getUserWithId();
    var name = user.getName();
    var users = List.of(user);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = users.size();
    var page = new PageImpl<>(users, pageable, size);
    when(userRepository.findByNameLikeIgnoreCase(name, pageable)).thenReturn(page);

    var usersPageFound = userService.findByNamePageable(name, pageable);

    assertThat(usersPageFound).isNotNull();
    assertThat(usersPageFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersPageFound.getTotalPages()).isEqualTo(size);
    assertThat(usersPageFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldReturnEmptyPageWhenFindByNameAndDoesNotExistAnyUserSaved() {
    var name = "Smith";
    var users = new ArrayList<User>();
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = 0;
    var page = new PageImpl<>(users, pageable, size);
    when(userRepository.findByNameLikeIgnoreCase(name, pageable)).thenReturn(page);

    var usersFound = userService.findByNamePageable(name, pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }
}