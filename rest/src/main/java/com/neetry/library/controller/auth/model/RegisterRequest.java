package com.neetry.library.controller.auth.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterRequest(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotNull @Positive
        Long phone
) {
}
