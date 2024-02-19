package com.neetry.library.user.auth.jwt.repository.model;

import com.neetry.library.user.repository.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String token;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    public UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Token token = (Token) o;
        return id != null && Objects.equals(id, token.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
