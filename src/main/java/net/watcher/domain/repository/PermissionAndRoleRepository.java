package net.watcher.domain.repository;

import net.watcher.domain.entities.Permission;
import net.watcher.domain.entities.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PermissionAndRoleRepository repository functionality
 * SuppressWarnings("unchecked")
 *
 * Please see the {@link Permission} class for true identity
 * @author Kostia
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class PermissionAndRoleRepository {
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Return user found by given params
     *
     * @param permissionNames     permission names
     * @return permission value of user or null if not found
     */
    public List<Permission> findPermissionsByNames(List<String> permissionNames) {
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder stringQuery = new StringBuilder();
        stringQuery.append("         from Permission where name in(:names)  ");
        Query query = currentSession.createQuery(stringQuery.toString());
        query.setParameter("names", permissionNames);
        return query.list();
    }

    /**
     * Return user found by given params
     *
     * @param rolesNames     roles names
     * @return roles value of user or null if not found
     */
    public List<Role> findRolesByNames(List<String> rolesNames) {
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder stringQuery = new StringBuilder();
        stringQuery.append("         from Role where name in(:names)  ");
        Query query = currentSession.createQuery(stringQuery.toString());
        query.setParameter("names", rolesNames);
        return query.list();
    }
}
