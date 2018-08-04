package net.watcher.domain.repository;

import net.watcher.domain.entities.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public Address findAddressByCountry(String country) {
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder stringQuery = new StringBuilder();
        stringQuery.append("from Address where country =:country");
        Query query = currentSession.createQuery(stringQuery.toString());
        query.setParameter("country", country);
        return (Address) query.uniqueResult();
    }
}
