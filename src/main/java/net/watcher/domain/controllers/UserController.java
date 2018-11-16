package net.watcher.domain.controllers;

import net.watcher.domain.dto.LoginWrapper;
import net.watcher.domain.requests.SignUpRequestModel;
import net.watcher.domain.responses.UserLoginResponseModel;
import net.watcher.domain.services.auth.CustomAuthenticationProvider;
import net.watcher.domain.services.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    /**
     * Try to login user
     *
     * @return UserLoginResponseModel user auth information
     * <p>
     * need fix user login with incorrect credentials
     * smoked ✓
     */
    @PostMapping("/login")
    public UserLoginResponseModel login(@RequestBody LoginWrapper loginWrapper) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginWrapper.getLogin(), loginWrapper.getPassword(), null);
        return customAuthenticationProvider.authenticate(authentication);
    }

    /**
     * Try to signUp user
     *
     * @param model user auth information
     * smoked ✓
     */
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public void signUp(@RequestBody @Valid SignUpRequestModel model) {
        userService.signUpUser(model);
    }

    /**
     * Try to logUt user
     * smoked ✓
     */
    @PostMapping("/logOut")
    public void logOut(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    /**
     * Try to logUt user
     * smoked ✓
     */
    @PostMapping("/confirm/{uuid}")
    public void activateUser(@PathVariable UUID uuid) {
        userService.activateUser(uuid);
    }

    @GetMapping("/test")
    @PermitAll
    public void test() {
        System.out.println("A");
    }
}
