package com.lmd.service.impl;

import com.lmd.pojo.OrderDetail;
import com.lmd.repository.OrderDetailRepository;
import com.lmd.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetaiRepo;
    @Override
    public List<OrderDetail> getOrderDetailByOrderId(int id) {
        return this.orderDetaiRepo.getOrderDetailByOrderId(id);
    }

    @Override
    public Integer getTotalQuantityByFoodId(int foodId, int resId) {
        return null;
    }

    @Override
    public Double getTotalRevenueByFoodId(int foodId, int resId) {
        return null;
    }
}
