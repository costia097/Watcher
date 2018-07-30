package net.watcher.domain.controllers;

import net.watcher.domain.dtos.UserLoginDto;
import net.watcher.domain.entities.Permission;
import net.watcher.domain.entities.User;
import net.watcher.domain.repository.PermissionRepository;
import net.watcher.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @GetMapping("/check")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void check() {
        System.out.println("All good");
    }

    @PostMapping("/login")
    @PermitAll
    public UserLoginDto login() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserName("name");
        userLoginDto.setRoles(Collections.singletonList("ROLE_USER"));
        userLoginDto.setPermissions(Collections.singletonList("write"));

        List<User> allUsers = userRepository.getAllUsers();

        List<Permission> allPermissions = permissionRepository.getAllPermissions();

        return userLoginDto;
    }
}
