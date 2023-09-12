package com.lmd.repository;

import com.lmd.pojo.Orderfood;

import java.util.List;

public interface OrderRepository {
    List<Orderfood> getOrderByUserId(int id);
    List<Orderfood> getOrderByRestaurantId(int id);

}
