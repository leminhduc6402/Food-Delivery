package com.lmd.repository;

import com.lmd.pojo.OrderDetail;

import java.util.List;

public interface OrderDetailRepository {
    List<OrderDetail> getOrderDetailByOrderId(int id);
    List<OrderDetail> getALlListOrderDetailByOrderId(int id);

    Integer getTotalQuantityByFoodId(int foodId, int resId);

    Double getTotalRevenueByFoodId(int foodId, int resId);
}
