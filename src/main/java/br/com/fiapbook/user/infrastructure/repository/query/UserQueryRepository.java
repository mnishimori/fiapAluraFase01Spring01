package br.com.fiapbook.user.infrastructure.repository.query;

import br.com.fiapbook.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface UserQueryRepository {

  @Query(value = """
          SELECT u
          FROM User u
          WHERE (:name IS NULL OR UPPER(TRIM(u.name)) LIKE CONCAT('%', UPPER(TRIM(:name)), '%'))
            OR (:email IS NULL OR UPPER(TRIM(u.email)) LIKE CONCAT('%', UPPER(TRIM(:email)), '%'))
      """)
  Page<User> queryUserByNameLikeIgnoreCaseOrEmail(String name, String email, Pageable pageable);
}
