package com.neetry.library.user.auth.service.model.command;

public record RegisterCommand(
        String name,
        String email,
        String password,
        Long phone,
        String country,
        String address,
        String postalZip
) {
}
