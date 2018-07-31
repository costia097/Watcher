package net.watcher.domain.services.auth;

import net.watcher.domain.entities.User;
import net.watcher.domain.services.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * CustomAuthenticationProvider functionality
 * first Authentication
 *
 * @author Kostia
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    /**
     * Try to login user
     *
     * @return  Authentication user auth information
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login =(String) authentication.getPrincipal();
        String password =  (String)authentication.getCredentials();
        User targetUser = userService.findByLogin(login, true,false);
        if (targetUser.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(login, password, targetUser.getRoles());
        } else {
            throw new BadCredentialsException("Wrong password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
