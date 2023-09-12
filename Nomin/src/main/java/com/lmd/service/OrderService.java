package com.lmd.service;

import com.lmd.pojo.Orderfood;

import java.util.List;

public interface OrderService {
    List<Orderfood> getOrderByUserId(int id);
    List<Orderfood> getOrderByRestaurantId(int id);
}
