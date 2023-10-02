package br.com.fiapbook.user.model.entity;

import br.com.fiapbook.shared.model.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.springframework.security.core.userdetails.UserDetails;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "user_management", name = "users")
@Where(clause = "deleted = false")
public class User extends BaseEntity implements UserDetails {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinTable(schema = "user_management", name = "users_authorities",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
  private List<Authority> authorities = new ArrayList<>();

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
