package com.neetry.library.user.auth.jwt.repository;

import com.neetry.library.user.auth.jwt.repository.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    @Query(value = """
            SELECT t FROM TokenEntity t INNER JOIN UserEntity u\s
            ON t.user.id = u.id\s
            WHERE u.id = :id AND (t.expired = FALSE OR t.revoked = FALSE )\s
            """)
    List<TokenEntity> findAllValidTokenByUser(Long id);

    Optional<TokenEntity> findByToken(String token);
}
