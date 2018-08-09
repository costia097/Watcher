package net.watcher.domain.services.core;

import net.watcher.domain.entities.Permission;
import net.watcher.domain.entities.Role;
import net.watcher.domain.repository.PermissionAndRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Permission service functionality
 *
 * Please see the {@link Permission} class for true identity
 * @author Kostia
 *
 */
@Service
public class PermissionAndRoleService {
    private static final List<String> PERMISSION_NAMES_FOR_NON_ACTIVE_USERS = Collections.singletonList("watch");
    private static final List<String> ROLE_NAMES_FOR_NON_ACTIVE_USERS = Collections.singletonList("ROLE_INACTIVE");
    private static final List<String> PERMISSION_NAMES_FOR_ACTIVE_USERS = Arrays.asList("watch", "read");
    private static final List<String> ROLE_NAMES_FOR_ACTIVE_USERS = Collections.singletonList("ROLE_USER");
    @Autowired
    private PermissionAndRoleRepository permissionAndRoleRepository;

    /**
     * Return permission for  non active user
     *
     * @return  user value of user or null if not found
     */
    @Transactional(readOnly = true)
    public List<Permission> resolvePermissionForNonActiveUser() {
        return permissionAndRoleRepository.findPermissionsByNames(PERMISSION_NAMES_FOR_NON_ACTIVE_USERS);
    }

    /**
     * Return roles for  non active user
     *
     * @return  user value of user or null if not found
     */
    @Transactional(readOnly = true)
    public List<Role> resolveRolesForNonActiveUser() {
        return permissionAndRoleRepository.findRolesByNames(ROLE_NAMES_FOR_NON_ACTIVE_USERS);
    }

    /**
     * Return permission for  non active user
     *
     * @return  user value of user or null if not found
     */
    @Transactional(readOnly = true)
    public List<Permission> resolvePermissionForActiveUser() {
        return permissionAndRoleRepository.findPermissionsByNames(PERMISSION_NAMES_FOR_ACTIVE_USERS);
    }

    /**
     * Return roles for  non active user
     *
     * @return  role
     */
    @Transactional(readOnly = true)
    public List<Role> resolveRolesForActiveUser() {
        return permissionAndRoleRepository.findRolesByNames(ROLE_NAMES_FOR_ACTIVE_USERS);
    }

}
