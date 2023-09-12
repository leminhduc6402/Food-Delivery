package com.lmd.service;

import com.lmd.pojo.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getOrderDetailByOrderId(int id);

    Integer getTotalQuantityByFoodId(int foodId, int resId);

    Double getTotalRevenueByFoodId(int foodId, int resId);
}
