package com.lmd.repository.impl;

import com.lmd.pojo.*;
import com.lmd.repository.FoodRepository;
import com.lmd.repository.ReceiptRepository;
import com.lmd.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ReceiptRepositoryImpl implements ReceiptRepository {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FoodRepository foodRepo;
    @Autowired
    private LocalSessionFactoryBean factory;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addReceipt(List<Cart> carts) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Map<Integer, List<Cart>> groupedCartsByRestaurantId = carts.stream()
                    .collect(Collectors.groupingBy(cart -> cart.getRestaurantId().getId()));

            for (List<Cart> cartGroup : groupedCartsByRestaurantId.values()) {
                Restaurant res = cartGroup.get(0).getRestaurantId();

                double totalAmount = 0;

                Orderfood order = new Orderfood();
                order.setUsersId(this.userRepo.getUserByUsername(authentication.getName()));
                order.setRestaurantsId(res);
                order.setStatus(0);
                order.setPaymentMethod(cartGroup.get(0).getPaymentMethod());
                order.setOrderDate(new Date());

                for (Cart cart : cartGroup) {
                    double subtotal = cart.getQuantity() * cart.getPrice();
                    totalAmount += subtotal;

                    OrderDetail od = new OrderDetail();
                    od.setQuantity(cart.getQuantity());
                    od.setFoodsId(this.foodRepo.getFoodById(cart.getId()));
                    od.setUnitPrice(cart.getPrice());
                    od.setOrdersId(order);

                    session.save(od);
                }

                totalAmount += res.getDeliveryFee();
                order.setTotalAmount(totalAmount);
                session.save(order);
            }

            return true;
        } catch (HibernateException ex){
            ex.printStackTrace();
            return false;
        }
    }


}
