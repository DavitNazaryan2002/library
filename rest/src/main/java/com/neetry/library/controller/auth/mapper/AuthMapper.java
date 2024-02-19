package com.neetry.library.controller.auth.mapper;

import com.neetry.library.controller.auth.model.AuthenticateRequest;
import com.neetry.library.controller.auth.model.RegisterRequest;
import com.neetry.library.user.auth.service.model.command.AuthenticateCommand;
import com.neetry.library.user.auth.service.model.command.RegisterCommand;
import org.mapstruct.Mapper;

@Mapper
public interface AuthMapper {

    RegisterCommand mapToRegisterCommand(RegisterRequest request);

    AuthenticateCommand mapToAuthenticateCommand(AuthenticateRequest request);
}
