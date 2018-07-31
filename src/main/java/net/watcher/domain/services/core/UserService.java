package net.watcher.domain.services.core;

import net.watcher.domain.entities.Permission;
import net.watcher.domain.entities.User;
import net.watcher.domain.repository.UserRepository;
import net.watcher.domain.requests.SignUpRequestModel;
import net.watcher.domain.responses.UserLoginResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * User service functionality
 *
 * Please see the {@link User} class for true identity
 * @author Kostia
 *
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Return user found by given id
     *
     * @param userId id of user
     * @return  user value of user or null if not found
     */
    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.getUserById(userId).orElse(null);
    }

    /**
     * Return user found by given params
     *
     * @param login     login of user
     * @param loadRoles or load roles
     * @param loadPermissions or load permissions
     * @return user value of user or null if not found
     */
    @Transactional(readOnly = true)
    public User findByLogin(String login, boolean loadRoles, boolean loadPermissions) {
        return userRepository.getUserByLogin(login, loadRoles, loadPermissions).orElse(null);
    }

    /**
     * Return user found by given params
     *
     * @param authentication authentication of user
     * @return UserLoginResponseModel user login model
     */
    @Transactional(readOnly = true)
    public UserLoginResponseModel loginUser(Authentication authentication) {
        String login = (String) authentication.getPrincipal();
        User targetUser = findByLogin(login, false, true);
        UserLoginResponseModel userLoginResponseModel = new UserLoginResponseModel();

        userLoginResponseModel.setUserName(login);
        userLoginResponseModel.setRoles(authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        userLoginResponseModel.setPermissions(targetUser.getPermissions()
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toList()));

        return userLoginResponseModel;
    }

    /**
     * Return user found by given params
     *
     * @param model signUp model of user
     * @return id of created user
     */
    @Transactional
    public Long signUpUser(SignUpRequestModel model) {
        return null;
    }

}
