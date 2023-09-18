package br.com.fiapbook.domain.service.user;

import br.com.fiapbook.domain.repository.user.UserRepository;
import br.com.fiapbook.domain.entity.user.User;
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
