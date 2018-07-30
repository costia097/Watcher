package net.watcher.domain.repository;

import net.watcher.domain.entities.Permission;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PermissionRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Permission> getAllPermissions() {
        Session currentSession = sessionFactory.getCurrentSession();
        List<Permission> from_permission = currentSession.createQuery("from Permission").list();
        return from_permission;
    }
}
