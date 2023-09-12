/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lmd.repository;

import com.lmd.pojo.Food;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nome
 */
public interface FoodRepository {

    List<Food> getAllFoods(Map<String, String> params);   
    
    List<Food> getFoodsByRestaurantId(int resId);
    
    Long countFood(Map<String, String> params);

    Food getFoodById(int id);

    boolean deleteFood(int id);    
    
    boolean addOrUpdateFood(Food f);
    
    Double getAverageRateForRestaurant(int resId);

    Double getAverageRateForFood(int foodId);
    
    boolean updateFood(Food f);

    Food addFood(Food food);
}
