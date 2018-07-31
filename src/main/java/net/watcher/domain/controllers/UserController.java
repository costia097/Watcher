package net.watcher.domain.controllers;

import net.watcher.domain.responses.UserLoginResponseModel;
import net.watcher.domain.requests.SignUpRequestModel;
import net.watcher.domain.services.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

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
     * @param  model user auth information
     * @return id of user
     */
    @PostMapping("/signUp")
    @PermitAll
    public Long signUp(@RequestBody @Valid SignUpRequestModel model) {
        return userService.signUpUser(model);
    }
}
