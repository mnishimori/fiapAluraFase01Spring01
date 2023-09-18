package br.com.fiapbook.user.model.repository;

import br.com.fiapbook.user.model.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

}
