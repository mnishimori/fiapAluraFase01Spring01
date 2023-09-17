package br.com.fiapbook.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Page<User> getAllUsersPaginated(Pageable pageable){
    return userRepository.findAll(pageable);
  }
}
