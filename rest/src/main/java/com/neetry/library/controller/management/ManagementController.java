package com.neetry.library.controller.management;

import com.neetry.library.user.auth.model.Role;
import com.neetry.library.user.service.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/management/v1/")
public class ManagementController {
    private final UserService userService;

    public ManagementController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/user/{id}/role/{role}")
    public ResponseEntity<Void> setUserRole(
            @PathVariable("id") @NotBlank @Positive Long id,
            @PathVariable("role") @NotBlank Role role
    ) {
        userService.updateUserRole(id, role);
        return ResponseEntity.ok().build();
    }
}
