package com.lmd.repository.impl;

import com.lmd.pojo.Food;
import com.lmd.pojo.OrderDetail;
import com.lmd.pojo.Orderfood;
import com.lmd.repository.StatsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class StatsRepoditotyImpl implements StatsRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private SimpleDateFormat dateFormat;
    @Override
    public List<Object[]> statsRevenue(Map<String, String> params, int id) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root food = criteriaQuery.from(Food.class);
        Root order = criteriaQuery.from(Orderfood.class);
        Root orderDetail = criteriaQuery.from(OrderDetail.class);

        criteriaQuery.multiselect(food.get("id"), food.get("name"), criteriaBuilder.sum(criteriaBuilder.prod(orderDetail.get("quantity"), orderDetail.get("unitPrice"))), criteriaBuilder.sum(orderDetail.get("quantity")));


        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(orderDetail.get("foodsId").get("id"), food.get("id")));
        predicates.add(criteriaBuilder.equal(orderDetail.get("ordersId").get("id"), order.get("id")));
        predicates.add(criteriaBuilder.equal(order.get("restaurantsId").get("id"), id));

        String year  = params.get("year");
        if (year != null && !year.isEmpty()){

            predicates.add(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, order.get("orderDate")), Integer.parseInt(year)));
            String quarter = params.get("quarter");
            if (quarter != null && !quarter.isEmpty()){
                predicates.addAll(Arrays.asList(
                   criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, order.get("orderDate")), Integer.parseInt(year)),
                   criteriaBuilder.equal(criteriaBuilder.function("QUARTER", Integer.class, order.get("orderDate")), Integer.parseInt(quarter))
                ));
            }

            String month = params.get("month");
            if (month != null && !month.isEmpty()){
                predicates.addAll(Arrays.asList(
                        criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, order.get("orderDate")), Integer.parseInt(year)),
                        criteriaBuilder.equal(criteriaBuilder.function("MONTH", Integer.class, order.get("orderDate")), Integer.parseInt(month))
                ));
            }
        }
        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        criteriaQuery.groupBy(food.get("id"));

        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
