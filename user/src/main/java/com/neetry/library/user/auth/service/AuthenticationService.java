package com.neetry.library.user.auth.service;

import com.neetry.library.user.auth.jwt.repository.TokenJpaRepository;
import com.neetry.library.user.auth.jwt.repository.model.TokenEntity;
import com.neetry.library.user.auth.jwt.service.JwtService;
import com.neetry.library.user.auth.model.Role;
import com.neetry.library.user.auth.service.model.AuthenticationData;
import com.neetry.library.user.auth.service.model.command.AuthenticateCommand;
import com.neetry.library.user.auth.service.model.command.RegisterCommand;
import com.neetry.library.user.repository.UserJpaRepository;
import com.neetry.library.user.repository.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

import static com.neetry.library.user.auth.model.Role.SUPER_ADMIN;
import static com.neetry.library.user.auth.model.Role.USER;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserJpaRepository repository;
    private final TokenJpaRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationData register(RegisterCommand command) {
        var user = UserEntity.builder()
                .name(command.name())
                .contactInfo(
                        UserEntity.ContactInfo.builder()
                                .email(command.email())
                                .phone(command.phone())
                                .build()
                )
                .password(passwordEncoder.encode(command.password()))
                .role(
                        superAdminOrDefault(command.email())
                )
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new AuthenticationData(jwtToken, refreshToken);
    }

    public AuthenticationData authenticate(
            AuthenticateCommand command,
            Consumer<AuthenticateCommand> authenticatorLambda
    ) {
        authenticatorLambda.accept(command);
        var user = repository.findByContactInfoEmail(command.email())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationData(jwtToken, refreshToken);
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationData refreshToken(String authHeader) {
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return AuthenticationData.empty();
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByContactInfoEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                return new AuthenticationData(accessToken, refreshToken);
            }
        }
        return AuthenticationData.empty();
    }

    private Role superAdminOrDefault(String email) {
        if (email.equals("davit@gmail.com")) {
            return SUPER_ADMIN;
        }

        return USER;
    }
}
