package br.com.fiapbook.user.infrastructure.repository;

import br.com.fiapbook.user.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);

  Page<User> findByNameLikeIgnoreCase(String name, Pageable pageable);

}
