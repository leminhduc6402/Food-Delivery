package com.lmd.repository.impl;

import com.lmd.pojo.Orderfood;
import com.lmd.repository.OrderRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Orderfood> getOrderByUserId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT distinct o FROM Orderfood o left join FETCH o.orderDetailSet WHERE o.usersId.id = :userId");
        query.setParameter("userId", id);
        return query.getResultList();
    }

    @Override
    public List<Orderfood> getOrderByRestaurantId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT distinct o FROM Orderfood o left join FETCH o.orderDetailSet WHERE o.restaurantsId.id = :restaurantId");
        query.setParameter("restaurantId", id);
        return query.getResultList();
    }
}
