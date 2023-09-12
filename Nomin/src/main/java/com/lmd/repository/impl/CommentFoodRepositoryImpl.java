/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.repository.impl;

import com.lmd.pojo.CommentsFood;
import com.lmd.repository.CommentFoodRepository;
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
public class CommentFoodRepositoryImpl implements CommentFoodRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<CommentsFood> getAllCommentByFoodId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT c FROM CommentsFood c WHERE c.foodsId.id =: id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public CommentsFood addComment(CommentsFood cmt) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.save(cmt);
            return cmt;
        } catch (HibernateException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }


}
