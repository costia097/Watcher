package net.watcher.domain.controllers;

import net.watcher.domain.dtos.UserLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.stream.Collectors;

/**
 * UserController controller functionality
 *
 * @author Kostia
 *
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {

    /**
     * Try to login user
     *
     * @return  UserLoginDto user auth information
     */
    @PostMapping("/login")
    @PermitAll
    public UserLoginDto login() {
        SecurityContext context = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) context.getAuthentication();
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserName((String) authentication.getPrincipal());
        userLoginDto.setRoles(authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        userLoginDto.setPermissions(null);

        return userLoginDto;
    }
}
