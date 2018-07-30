package net.watcher.domain.services.core;

import net.watcher.domain.entities.User;
import net.watcher.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param login login of user
     * @param password password of user
     * @param loadRoles or load roles
     * @return  user value of user or null if not found
     */
    @Transactional(readOnly = true)
    public User findByLoginAndPassword(String login, String password, boolean loadRoles) {
        return userRepository.getUserByLoginAndPassword(login, password, loadRoles).orElse(null);
    }


}
