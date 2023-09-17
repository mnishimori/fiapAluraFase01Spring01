package br.com.fiapbook.shared.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    protected UUID id;

    @Column(nullable = false)
    protected Boolean deleted;

    @Version
    @Column(nullable = false)
    protected Long version;

    @CreationTimestamp
    @Column(nullable = false)
    protected OffsetDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false)
    protected OffsetDateTime updated_at;

}
