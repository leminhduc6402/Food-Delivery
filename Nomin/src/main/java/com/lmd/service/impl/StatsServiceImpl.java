package com.lmd.service.impl;

import com.lmd.pojo.Food;
import com.lmd.pojo.FoodStatistic;
import com.lmd.repository.OrderDetailRepository;
import com.lmd.repository.StatsRepository;
import com.lmd.repository.impl.OrderDetailRepositoryImpl;
import com.lmd.service.FoodService;
import com.lmd.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private FoodService foodService;
    @Autowired
    private StatsRepository statsRepository;

    @Override
    public List<FoodStatistic> calculateFoodSales(int resId) {
        List<Food> listFood = this.foodService.getFoodsByRestaurantId(resId);
        List<FoodStatistic> statisticList = new ArrayList<>();
        for (Food food : listFood){
            Integer foodId =  food.getId();
            Integer totalQuantity = this.orderDetailRepository.getTotalQuantityByFoodId(foodId, resId);
            Double totalRevenue = orderDetailRepository.getTotalRevenueByFoodId(foodId, resId);

            FoodStatistic foodStatistic = new FoodStatistic();
            foodStatistic.setFoodId(foodId);
            foodStatistic.setFoodName(food.getName());
            foodStatistic.setTotalQuantity(totalQuantity);
            foodStatistic.setTotalRevenue(totalRevenue);

            statisticList.add(foodStatistic);

        }
        return statisticList;
    }

    @Override
    public List<Object[]> statsRevenue(Map<String, String> params, int id) {
        return this.statsRepository.statsRevenue(params, id);
    }
}
