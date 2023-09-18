package br.com.fiapbook.domain.user.service;

import br.com.fiapbook.domain.user.entity.User;
import br.com.fiapbook.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User save(User user){
    return userRepository.save(user);
  }

  public Page<User> getAllUsersPaginated(Pageable pageable){
    return userRepository.findAll(pageable);
  }
}
