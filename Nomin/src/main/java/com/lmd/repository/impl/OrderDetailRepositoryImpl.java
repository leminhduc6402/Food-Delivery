package com.lmd.repository.impl;

import com.lmd.pojo.OrderDetail;
import com.lmd.repository.OrderDetailRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class OrderDetailRepositoryImpl implements OrderDetailRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<OrderDetail> getOrderDetailByOrderId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT od FROM OrderDetail od WHERE od.ordersId.id = :orderId");
        query.setParameter("orderId", id);
        return query.getResultList();
    }

    @Override
    public List<OrderDetail> getALlListOrderDetailByOrderId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT od FROM OrderDetail od WHERE od.ordersId.id = :orderId");
        query.setParameter("orderId", id);
        return query.getResultList();
    }

    @Override
    public Integer getTotalQuantityByFoodId(int foodId, int resId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT SUM(od.quantity) FROM OrderDetail od WHERE od.foodsId.id = :foodId AND od.ordersId.restaurantsId.id = :resId");
        query.setParameter("foodId", foodId);
        query.setParameter("resId", resId);
        Integer total = (Integer) query.getSingleResult();
        return total;
    }

    @Override
    public Double getTotalRevenueByFoodId(int foodId, int resId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT SUM(od.quantity * od.unitPrice) FROM OrderDetail od WHERE od.foodsId.id = :foodId AND od.ordersId.restaurantsId.id = :resId");
        query.setParameter("foodId", foodId);
        query.setParameter("resId", resId);
        return (Double) query.getSingleResult();
    }
}
