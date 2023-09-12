/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.repository.impl;

import com.lmd.pojo.Food;
import com.lmd.pojo.Restaurant;

import com.lmd.repository.FoodRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import org.hibernate.HibernateException;
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
public class FoodRepositoryImpl implements FoodRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public List<Food> getAllFoods(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Food> criteriaQuery = criteriaBuilder.createQuery(Food.class);
        Root root = criteriaQuery.from(Food.class);
        criteriaQuery.select(root);

        Join<Food, Restaurant> restaurantJoin = root.join("restaurantId", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {

            String kw = params.get("kw");
            String restaurantName = params.get("restaurantName");

            String resId = params.get("resId");
            if (resId != null && !resId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(restaurantJoin.get("id"), resId));
            }
            //Tra cứu theo tên món ăn
            if (kw != null && !kw.isEmpty()) {
//                predicates.add(criteriaBuilder.like(root.get("name"), "%" + kw + "%"));
//                Predicate foodName = criteriaBuilder.like(root.get("name"), "%" + kw + "%");
                if (isNumeric(kw)) {
                    Predicate foodPrice = criteriaBuilder.lessThanOrEqualTo(root.get("price"), Double.parseDouble(kw));
                    predicates.add(foodPrice);
                } else {
                    Predicate foodName = criteriaBuilder.like(root.get("name"), "%" + kw + "%");
                    Predicate foodType = criteriaBuilder.like(root.get("foodType"), "%" + kw + "%");
                    predicates.add(criteriaBuilder.or(foodName, foodType));
                }

            }

            //Tra cứu theo tên nhà hàng
            if (restaurantName != null && !restaurantName.isEmpty()) {
                predicates.add(criteriaBuilder.like(restaurantJoin.get("name"), "%" + restaurantName + "%"));
            }

            //Kết hợp câu truy vấn
//            criteriaQuery.where(predicates.toArray(Predicate[]::new));
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        }

        //Sắp xếp tăng dần
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
    public Long countFood(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root root = criteriaQuery.from(Food.class);
        criteriaQuery.select(criteriaBuilder.count(root));

        Join<Food, Restaurant> restaurantJoin = root.join("restaurantId", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            // Áp dụng các điều kiện tìm kiếm như trong getAllFoods
            String kw = params.get("kw");
            String minPrice = params.get("minPrice");
            String maxPrice = params.get("maxPrice");
            String foodType = params.get("foodType");
            String restaurantName = params.get("restaurantName");
            //Tra cứu theo tên món ăn
            if (kw != null && !kw.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + kw + "%"));
            }

            //Tra cứu theo tên nhà hàng
            if (restaurantName != null && !restaurantName.isEmpty()) {
                predicates.add(criteriaBuilder.like(restaurantJoin.get("name"), "%" + restaurantName + "%"));
            }

            //Tra cứu theo giá
            if (minPrice != null && !minPrice.isEmpty()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(minPrice)));
            }
            if (maxPrice != null && !maxPrice.isEmpty()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Double.parseDouble(maxPrice)));
            }

            //Tra cứu theo loại thức ăn
            if (foodType != null && !foodType.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("foodType"), "%" + foodType + "%"));
            }

            //Kết hợp câu truy vấn
//            criteriaQuery.where(predicates.toArray(Predicate[]::new));
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        Query query = session.createQuery(criteriaQuery);
        return (Long) query.getSingleResult();
    }

    @Override
    public Food getFoodById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Food.class, id);
    }

    @Override
    public boolean deleteFood(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Food f = this.getFoodById(id);
        try {
            session.delete(f);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Food> getFoodsByRestaurantId(int resId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT f FROM Food f WHERE f.restaurantId.id = :resId");
        query.setParameter("resId", resId);

        return query.getResultList();
    }

    @Override
    public boolean addOrUpdateFood(Food f) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (f.getId() == null) {
                session.save(f);
            } else {
                session.update(f);
            }
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Double getAverageRateForRestaurant(int resId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT AVG(c.rate) FROM CommentsFood c WHERE c.foodsId.restaurantId.id = :restaurantId");
        query.setParameter("restaurantId", resId);
        
        return (Double) query.getSingleResult();
    }

    @Override
    public Double getAverageRateForFood(int foodId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT AVG(cf.rate) FROM CommentsFood cf WHERE cf.foodsId.id = :foodId");
        query.setParameter("foodId", foodId);
        return (Double) query.getSingleResult();
    }

    @Override
    public boolean updateFood(Food f) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (f.getId() != null) {
                session.update(f);
            }
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Food addFood(Food food) {
        Session session = this.factory.getObject().getCurrentSession();
        session.save(food);
        return food;
    }
}
