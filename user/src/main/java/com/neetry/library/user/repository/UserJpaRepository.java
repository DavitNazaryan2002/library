package com.neetry.library.user.repository;

import com.neetry.library.user.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByContactInfoEmail(String email);

    Optional<UserEntity> findByName(String name);
}
