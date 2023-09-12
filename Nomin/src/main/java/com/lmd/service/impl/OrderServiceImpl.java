package com.lmd.service.impl;

import com.lmd.pojo.Orderfood;
import com.lmd.repository.OrderRepository;
import com.lmd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepo;

    @Override
    public List<Orderfood> getOrderByUserId(int id) {
        return this.orderRepo.getOrderByUserId(id);
    }

    @Override
    public List<Orderfood> getOrderByRestaurantId(int id) {
        return this.orderRepo.getOrderByRestaurantId(id);
    }
}
