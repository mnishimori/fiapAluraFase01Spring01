package br.com.fiapbook.domain.repository.user;

import br.com.fiapbook.domain.entity.user.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

}
