package net.watcher.domain.repository;

import net.watcher.domain.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<User> getAllUsers() {
        Session currentSession = sessionFactory.getCurrentSession();
        List from_user = currentSession.createQuery("from User").list();
        List from_role = currentSession.createQuery("from Role").list();
        return null;
    }
}
