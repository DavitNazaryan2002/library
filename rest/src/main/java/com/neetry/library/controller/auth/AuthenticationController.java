package com.neetry.library.controller.auth;

import com.neetry.library.controller.auth.mapper.AuthMapper;
import com.neetry.library.controller.auth.model.AuthenticateRequest;
import com.neetry.library.controller.auth.model.RegisterRequest;
import com.neetry.library.user.auth.service.AuthenticationService;
import com.neetry.library.user.auth.service.model.AuthenticationData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/v1")
public class AuthenticationController {

    private final AuthenticationService service;

    private final AuthMapper mapper;

    private final AuthenticationManager manager;

    public AuthenticationController(AuthenticationService service, AuthMapper mapper, AuthenticationManager manager) {
        this.service = service;
        this.mapper = mapper;
        this.manager = manager;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationData> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(mapper.mapToRegisterCommand(request)));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationData> authenticate(
            @RequestBody AuthenticateRequest request
    ) {
        return ResponseEntity.ok(
                service.authenticate(
                        mapper.mapToAuthenticateCommand(request),
                        command -> {
                            manager.authenticate(
                                    new UsernamePasswordAuthenticationToken(
                                            command.email(),
                                            command.password()
                                    )
                            );
                        }
                )
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationData> refreshToken(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                service.refreshToken(request.getHeader(HttpHeaders.AUTHORIZATION))
        );
    }
}
