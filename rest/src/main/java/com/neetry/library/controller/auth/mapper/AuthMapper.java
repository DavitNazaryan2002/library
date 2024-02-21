package com.neetry.library.controller.auth.mapper;

import com.neetry.library.controller.auth.model.AuthenticateRequest;
import com.neetry.library.controller.auth.model.RegisterRequest;
import com.neetry.library.user.auth.service.model.command.AuthenticateCommand;
import com.neetry.library.user.auth.service.model.command.RegisterCommand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring"
)
public interface AuthMapper {

    RegisterCommand mapToRegisterCommand(RegisterRequest request);

    AuthenticateCommand mapToAuthenticateCommand(AuthenticateRequest request);
}
