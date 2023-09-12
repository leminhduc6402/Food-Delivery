package com.lmd.service;

import com.lmd.pojo.FoodStatistic;

import java.util.List;
import java.util.Map;

public interface StatsService {
    List<FoodStatistic> calculateFoodSales(int resId);

    List<Object[]> statsRevenue(Map<String, String> params, int id);
}
