package net.watcher.domain.repository;

import net.watcher.domain.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository repository functionality
 * SuppressWarnings("unchecked")
 *
 * Please see the {@link User} class for true identity
 * @author Kostia
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Return user found by given id
     *
     * @param userId id of user
     * @return  user value of user or null if not found
     */
    public Optional<User> getUserById(Long userId) {
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder stringQuery = new StringBuilder().append("from User where id=:id");
        Query userQuery = currentSession.createQuery(stringQuery.toString());
        userQuery.setParameter("id", userId);
        return userQuery.uniqueResultOptional();
    }

    /**
     * Return user found by given params
     *
     * @param login    login of user
     * @param password password of user
     * @param loadRoles or load roles
     * @return user value of user or null if not found
     */
    public Optional<User> getUserByLoginAndPassword(String login, String password, boolean loadRoles) {
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder stringQuery = new StringBuilder().append("   from User u ");
        if (loadRoles) {
            stringQuery.append("  join fetch u.roles");
        }
        stringQuery.append("          where u.login =:login " +
                "                         and u.password=:password");
        Query query = currentSession.createQuery(stringQuery.toString());
        query.setParameter("login", login);
        query.setParameter("password", password);
        return query.uniqueResultOptional();
    }
}
