package com.neetry.library.user.service;

import com.neetry.library.user.auth.model.Role;
import com.neetry.library.user.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

import static com.neetry.library.user.auth.model.Role.SUPER_ADMIN;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public UserService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public void updateUserRole(Long userId, Role newRole) {
        final var user = userJpaRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole().equals(SUPER_ADMIN)) {
            throw new RuntimeException("Can't change role of super admin");
        }

        user.setRole(newRole);
        userJpaRepository.save(user);
    }
}
