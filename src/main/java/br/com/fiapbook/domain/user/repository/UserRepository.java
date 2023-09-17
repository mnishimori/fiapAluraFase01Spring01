package br.com.fiapbook.domain.user.repository;

import br.com.fiapbook.domain.user.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

}
