package br.com.fiapbook.user.model.service;

import br.com.fiapbook.user.model.entity.User;
import br.com.fiapbook.user.infrastructure.repository.UserRepository;
import br.com.fiapbook.user.model.messages.UserMessages;
import jakarta.persistence.NoResultException;
import java.util.Optional;
import java.util.UUID;
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

  public User findByEmailRequired(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new NoResultException(UserMessages.USER_EMAIL_NOT_FOUND.formatted(email)));
  }

  public Page<User> findByNamePageable(String name, Pageable pageable) {
    return userRepository.findByNameLikeIgnoreCase(name, pageable);
  }

  public Page<User> queryUserByNameLikeIgnoreCaseOrEmail(String name, String email,
      Pageable pageable) {
    return userRepository.queryUserByNameLikeIgnoreCaseOrEmail(name, email, pageable);
  }

  public Optional<User> findById(UUID uuid) {
    return userRepository.findById(uuid);
  }
}
