package com.neetry.library.controller.auth.model;

import jakarta.validation.constraints.NotBlank;

public record AuthenticateRequest(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
