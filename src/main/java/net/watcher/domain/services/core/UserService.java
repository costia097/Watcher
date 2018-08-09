package net.watcher.domain.services.core;

import net.watcher.domain.convertors.UserConverter;
import net.watcher.domain.entities.Address;
import net.watcher.domain.entities.Permission;
import net.watcher.domain.entities.Role;
import net.watcher.domain.entities.User;
import net.watcher.domain.repository.UserRepository;
import net.watcher.domain.requests.SignUpRequestModel;
import net.watcher.domain.responses.UserLoginResponseModel;
import net.watcher.domain.services.core.intefaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
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
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private PermissionAndRoleService permissionAndRoleService;
    @Autowired
    private EmailService emailService;

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
        return userRepository.getUserByLogin(login, loadRoles, loadPermissions,false).orElse(null);
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
        if (targetUser == null) {
            return null;
        }
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
     * Method
     *
     * @param model signUp model of user
     */
    @Transactional
    public void signUpUser(SignUpRequestModel model) {
        User user = userConverter.convertFromSignUpUserModel(model);
        addNonActiveUserPermissionAndRole(user);
        fillAddress(model, user);
        user.setUuid(UUID.randomUUID());
        userRepository.saveUser(user);
        emailService.sendEmailToPerson(model.getEmail(), "Confirm registration", "Hello, dear " + model.getFirstName() + "Your link is:" +
                "http://localhost:4200/signUp/confirm?param=" + user.getUuid());
    }

    /**
     * Method
     *
     * @param user param
     */
    private void addNonActiveUserPermissionAndRole(User user) {
        user.setPermissions(new HashSet<>(permissionAndRoleService.resolvePermissionForNonActiveUser()));
        user.setRoles(new HashSet<>(permissionAndRoleService.resolveRolesForNonActiveUser()));
    }

    /**
     * Method
     *
     * @param model param
     * @param user param
     */
    private void fillAddress(SignUpRequestModel model, User user) {
        Address address = new Address();
        address.setCountry(model.getCountry());
        address.setAddressLine(model.getAddress());
        user.setAddress(address);
    }

    /**
     * Method
     *
     * @param uuid param
     *
     *  //        targetUser.setRoles(roles);
     * //        targetUser.setPermissions(permissions);
     *  when trying just set new collection of role and permission and update see next logs :
     *2018-08-05 06:06:22.041  WARN 6940 --- [nio-9090-exec-4] o.h.e.loading.internal.LoadContexts      : HHH000100: Fail-safe cleanup (collections) : org.hibernate.engine.loading.internal.CollectionLoadContext@5053e657<rs=HikariProxyResultSet@180270629 wrapping com.mysql.jdbc.JDBC42ResultSet@79be96f0>
     * 2018-08-05 06:06:22.042  WARN 6940 --- [nio-9090-exec-4] o.h.e.loading.internal.LoadContexts      : HHH000100: Fail-safe cleanup (collections) : org.hibernate.engine.loading.internal.CollectionLoadContext@72b466e7<rs=HikariProxyResultSet@189106070 wrapping com.mysql.jdbc.JDBC42ResultSet@62d990c>
     * 2018-08-05 06:06:22.042  WARN 6940 --- [nio-9090-exec-4] o.h.e.l.internal.CollectionLoadContext   : HHH000160: On CollectionLoadContext#cleanup, localLoadingCollectionKeys contained [1] entries
     * 2018-08-05 06:06:22.042  WARN 6940 --- [nio-9090-exec-4] o.h.e.loading.internal.LoadContexts      : HHH000100: Fail-safe cleanup (collections) : org.hibernate.engine.loading.internal.CollectionLoadContext@31dad01<rs=HikariProxyResultSet@34637018 wrapping com.mysql.jdbc.JDBC42ResultSet@10796a32>
     * 2018-08-05 06:06:22.042  WARN 6940 --- [nio-9090-exec-4] o.h.e.loading.internal.LoadContexts      : HHH000100: Fail-safe cleanup (collections) : org.hibernate.engine.loading.internal.CollectionLoadContext@43269a5d<rs=HikariProxyResultSet@1803698986 wrapping com.mysql.jdbc.JD
     */
    @Transactional
    public void activateUser(UUID uuid) {
        User targetUser = userRepository.findByUuid(uuid);
        if (targetUser == null || targetUser.isActive()) {
            return;
        }
        targetUser.setActive(true);
        List<Permission> permissions = permissionAndRoleService.resolvePermissionForActiveUser();
        List<Role> roles = permissionAndRoleService.resolveRolesForActiveUser();
        targetUser.getPermissions().clear();
        targetUser.getRoles().clear();
        permissions.forEach(permission -> targetUser.getPermissions().add(permission));
        roles.forEach(role -> targetUser.getRoles().add(role));
        userRepository.updateUser(targetUser);
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

}
