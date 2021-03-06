package net.watcher.domain.repository;

import net.watcher.domain.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
     * @param login           login of user
     * @param loadRoles       or load roles
     * @param loadPermissions or load permissions
     * @param loadAddress or load address
     * @return user value of user or null if not found
     */
    public Optional<User> getUserByLogin(String login, boolean loadRoles, boolean loadPermissions, boolean loadAddress) {
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder stringQuery = new StringBuilder().append("   from User u ");
        if (loadRoles) {
            stringQuery.append("  join fetch u.roles");
        }
        if (loadPermissions) {
            stringQuery.append(" join fetch u.permissions");
        }
        if (loadAddress) {
            stringQuery.append("  join fetch u.address");
        }
        stringQuery.append("     where u.login =:login ");
        Query query = currentSession.createQuery(stringQuery.toString());
        query.setParameter("login", login);
        return query.uniqueResultOptional();
    }

    /**
     * Return user found by given params
     *
     * @param user     user to save
     */
    public void saveUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    /**
     * Return user found by given params
     *
     * @param uuid param
     */
    public User findByUuid(UUID uuid) {
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder stringQuery = new StringBuilder();
        stringQuery.append("   from User where uuid=:uuid");
        Query query = currentSession.createQuery(stringQuery.toString());
        query.setParameter("uuid", uuid);
        return (User) query.uniqueResult();
    }

    /**
     * Return user found by given params
     *
     * @param user param
     */
    public void updateUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(user);
    }

    /**
     * Return user found by given params
     *
     */
    public List<String> findAllLogins() {
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder stringQuery = new StringBuilder();
        stringQuery.append("SELECT u.login From User u");
        Query query = currentSession.createQuery(stringQuery.toString());
        return query.list();
    }

    /**
     * Return user found by given paramsl
     */
    public List<String> findAllEmails() {
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder stringQuery = new StringBuilder();
        stringQuery.append("SELECT u.email From User u");
        Query query = currentSession.createQuery(stringQuery.toString());
        return query.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
