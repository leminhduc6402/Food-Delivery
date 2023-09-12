/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.repository.impl;

import com.lmd.pojo.User;
import com.lmd.repository.UserRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nome
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private Environment env;
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Override
    public List<User> getAllUsers(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String kw = params.get("kw");
            String role = params.get("role");
            //Tra cứu theo tên người dùng
            if (kw != null && !kw.isEmpty()) {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + kw + "%");
                Predicate phonePredicate = criteriaBuilder.like(root.get("phone"), "%" + kw + "%");

                predicates.add(criteriaBuilder.or(namePredicate, phonePredicate));

            }
            if (role != null && !role.isEmpty()) {
                if (role.equals("1")) {
                    predicates.add(criteriaBuilder.equal(root.get("role"), "ROLE_RESTAURANT"));
                    predicates.add(criteriaBuilder.equal(root.get("restaurant").get("status"), 0));
                } else {
                    Predicate rolePredicate = criteriaBuilder.equal(root.get("role"), role);
                    predicates.add(rolePredicate);
                }
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
    public User getUserById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public boolean addOrUpdateUser(User u) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (u.getId() == null) {
                session.save(u);
            } else {
                session.update(u);
            }
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long countUser(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(root));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.notEqual(root.get("role"), 1));

        if (params != null) {
            String kw = params.get("kw");
            String role = params.get("role");
            //Tra cứu theo tên người dùng
            if (kw != null && !kw.isEmpty()) {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + kw + "%");
                Predicate phonePredicate = criteriaBuilder.like(root.get("phone"), "%" + kw + "%");

                predicates.add(criteriaBuilder.or(namePredicate, phonePredicate));

            }
            if (role != null && !role.isEmpty()) {
                Predicate rolePredicate = criteriaBuilder.like(root.get("role"), "%" + role + "%");
            }

        }
        //Kết hợp câu truy vấn
//        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        Query query = session.createQuery(criteriaQuery);
        return (Long) query.getSingleResult();
    }

    @Override
    public User getUserByUsername(String uname) {
        Session s = this.factory.getObject().getCurrentSession();
        Query query = s.createQuery("FROM User WHERE username=:un");
        query.setParameter("un", uname);

        return (User) query.getSingleResult();
    }

    @Override
    public boolean authUser(String username, String password) {
        User u = this.getUserByUsername(username);
        return this.passEncoder.matches(password, u.getPassword());
    }

    @Override
    public boolean deleteUser(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        User u = this.getUserById(id);
        try {
            session.delete(u);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User addUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(user);
        return user;
    }

}
