package br.com.fiapbook.user.model.service;

import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.infrastructure.repository.UserRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public Page<User> getAllUsersPaginated(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
