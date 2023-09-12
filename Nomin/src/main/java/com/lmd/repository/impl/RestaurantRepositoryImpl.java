/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.repository.impl;

import com.lmd.pojo.Restaurant;
import com.lmd.pojo.User;
import com.lmd.repository.RestaurantRepository;
import com.lmd.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nome
 */
@Repository
@Transactional
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;
    @Autowired
    private UserService userService;
    
    @Override
    public List<Restaurant> getAllRestaurants(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);
        Root root = criteriaQuery.from(Restaurant.class);
        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (params != null) {
            String kw = params.get("kw");
            String minPrice = params.get("minPrice");
            String maxPrice = params.get("maxPrice");
            String restaurantStatus = params.get("status");

            //Tra cứu theo tên nhà hàng
            if (kw != null && !kw.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + kw + "%"));
            }
            //Tra cứu theo phí vận chuyển
            if (isNumeric(kw)) {
                Predicate deliveryFee = criteriaBuilder.lessThanOrEqualTo(root.get("deliveryFee"), Double.parseDouble(kw));
                predicates.add(deliveryFee);
            }
            //Tra cứu theo trạng thái
            if (restaurantStatus != null && !restaurantStatus.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), Double.parseDouble(restaurantStatus)));
            }

            //Kết hợp câu truy vấn
//            criteriaQuery.where(predicates.toArray(Predicate[]::new));
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
        Query query = session.createQuery(criteriaQuery);

        if (params != null) {
            String page = (params.get("page") == null) ? "1" : params.get("page");
            int p = Integer.parseInt(page);
            int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

            query.setFirstResult((p - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
//        Query query = s.createQuery("Select r From Restaurant r Join Fetch r.foodSet Where r.id =: idRes");
//        query.setParameter("idRes", id);
//        Restaurant res = (Restaurant) query.getSingleResult();
//        return res;
        return s.get(Restaurant.class, id);
    }

    @Override
    public Long countRestaurant(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root root = criteriaQuery.from(Restaurant.class);
        criteriaQuery.select(criteriaBuilder.count(root));

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String restaurantName = params.get("kw");
            String minPrice = params.get("minPrice");
            String maxPrice = params.get("maxPrice");
            String restaurantStatus = params.get("status");

            //Tra cứu theo tên nhà thàng
            if (restaurantName != null && !restaurantName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + restaurantName + "%"));
            }
            //Tra cứu theo phí vận chuyển
            if (minPrice != null && !minPrice.isEmpty()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deliveryFee"), Double.parseDouble(minPrice)));
            }
            if (maxPrice != null && !maxPrice.isEmpty()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deliveryFee"), Double.parseDouble(maxPrice)));
            }
            //Tra cứu theo trạng thái
            if (restaurantStatus != null && !restaurantStatus.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), Double.parseDouble(restaurantStatus)));
            }

            //Kết hợp câu truy vấn
//            criteriaQuery.where(predicates.toArray(Predicate[]::new));
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

//        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        Query query = session.createQuery(criteriaQuery);
        return (Long) query.getSingleResult();
    }

    @Override
    public Boolean addOrUpdateRestaurant(Restaurant res) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (res.getId() == null) {
                session.save(res);
            } else {
                // Kiểm tra status của nhà hàng
                if (res.getStatus() == null) {
                    // Mặc định là 0
                    res.setStatus(0);
                } 
                session.update(res);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Restaurant addRestaurant(Restaurant res) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(res);
        return res;
    }

    @Override
    public Restaurant getRestaurantByUserId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT r FROM Restaurant r WHERE r.userId.id = :userId");
        query.setParameter("userId", id);
        return (Restaurant) query.getSingleResult();
    }

    @Override
    public Boolean updateRestaurant(Restaurant res) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (res.getId() != null) {
                session.update(res);
            }
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }
}
