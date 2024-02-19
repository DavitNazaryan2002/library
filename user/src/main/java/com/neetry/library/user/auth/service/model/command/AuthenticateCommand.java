package com.neetry.library.user.auth.service.model.command;

public record AuthenticateCommand(
        String email,
        String password
) {
}
