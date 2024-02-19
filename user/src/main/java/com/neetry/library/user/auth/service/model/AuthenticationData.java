package com.neetry.library.user.auth.service.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AuthenticationData(
        String accessToken,
        String refreshToken
) {
    public static AuthenticationData empty() {
        return new AuthenticationData(null, null);
    }
}
