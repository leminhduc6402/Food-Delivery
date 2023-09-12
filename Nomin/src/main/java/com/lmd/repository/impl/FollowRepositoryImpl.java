package com.lmd.repository.impl;

import com.lmd.pojo.Follow;
import com.lmd.pojo.User;
import com.lmd.repository.FollowRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class FollowRepositoryImpl implements FollowRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Boolean addFollow(Follow follow) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.save(follow);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean unFollow(int resrautantId, int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT f FROM Follow f WHERE f.restaurantsId.id = :restaurantId AND f.usersId.id = :userId");
        query.setParameter("restaurantId", resrautantId);
        query.setParameter("userId", userId);
        Follow follow = (Follow) query.getSingleResult();
        try {
            session.delete(follow);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Follow> getListFollowByUserI(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT follow FROM Follow follow WHERE follow.usersId.id =:userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<User> getListFollowByRestaurantId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT follow.usersId FROM Follow follow WHERE follow.restaurantsId.id =:id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Long countFollowerByRestaurantId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT COUNT('follow') FROM Follow follow WHERE  follow.restaurantsId.id =:id");
        query.setParameter("id", id);
        return (Long) query.getSingleResult();
    }


}
