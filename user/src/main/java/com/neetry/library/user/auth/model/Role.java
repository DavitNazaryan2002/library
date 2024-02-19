package com.neetry.library.user.auth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.neetry.library.user.auth.model.Permissions.*;

@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(
                    SEARCH_BOOKS,
                    MAKE_PURCHASE
            )
    ),
    ADMIN(
            Set.of(
                    SEARCH_BOOKS,
                    MANAGE_USERS,
                    MANAGE_BOOKS
            )
    ),
    SUPER_ADMIN(
            Set.of(
                    SEARCH_BOOKS,
                    MANAGE_USERS,
                    MANAGE_BOOKS,
                    ACCESS_SALES_REPORT,
                    GRANT_ROLES
            )
    );

    @Getter
    private final Set<String> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
