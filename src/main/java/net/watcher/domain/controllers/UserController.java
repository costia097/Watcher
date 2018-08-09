package net.watcher.domain.controllers;

import net.watcher.domain.responses.UserLoginResponseModel;
import net.watcher.domain.requests.SignUpRequestModel;
import net.watcher.domain.services.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

/**
 * UserController controller functionality
 *
 * @author Kostia
 *
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Try to login user
     *
     * @return  UserLoginResponseModel user auth information
     *
     * need fix user login with incorrect credentials
     * smoked ✓
     */
    @PostMapping("/login")
    @PermitAll
    public UserLoginResponseModel login() {
        SecurityContext context = SecurityContextHolder.getContext();
        return userService.loginUser(context.getAuthentication());
    }

    /**
     * Try to signUp user
     *
     * @param model user auth information
     * smoked ✓
     */
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public void signUp(@RequestBody @Valid SignUpRequestModel model) {
        userService.signUpUser(model);
    }

    /**
     * Try to logUt user
     * smoked ✓
     */
    @PostMapping("/logOut")
    @PermitAll
    public void logOut(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    /**
     * Try to logUt user
     * smoked ✓
     */
    @PostMapping("/confirm/{uuid}")
    @PermitAll
    public void activateUser(@PathVariable UUID uuid) {
        userService.activateUser(uuid);
    }
}
