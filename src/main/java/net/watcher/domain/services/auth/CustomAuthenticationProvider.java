package net.watcher.domain.services.auth;

import net.watcher.domain.entities.Permission;
import net.watcher.domain.entities.Role;
import net.watcher.domain.entities.User;
import net.watcher.domain.responses.UserLoginResponseModel;
import net.watcher.domain.services.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CustomAuthenticationProvider functionality
 * first Authentication
 *
 * @author Kostia
 *
 */
@Component
public class CustomAuthenticationProvider {
    @Autowired
    private UserService userService;

    /**
     * Try to authenticate user
     *
     */
    public UserLoginResponseModel authenticate(Authentication authentication) throws AuthenticationException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");

        UserLoginResponseModel model = new UserLoginResponseModel();

        String login = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User targetUser = userService.findByLogin(login, true, true);
        if (targetUser == null) {
            throw new BadCredentialsException("Bad creds");
        } else if (targetUser.getPassword().equals(password)) {
            SecurityContext context = SecurityContextHolder.getContext();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login, password, targetUser.getRoles());
            context.setAuthentication(usernamePasswordAuthenticationToken);

            List<String> rolesConverted = targetUser.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
            List<String> permissionsConverted = targetUser.getPermissions().stream()
                    .map(Permission::getName)
                    .collect(Collectors.toList());

            model.setRoles(rolesConverted);
            model.setPermissions(permissionsConverted);
            model.setUserName(login);
        } else {
            throw new DisabledException("your account is not active");
        }
        return model;
    }
}
