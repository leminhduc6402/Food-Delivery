/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lmd.service;

import com.lmd.pojo.Food;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nome
 */
public interface FoodService {

    List<Food> getAllFoods(Map<String, String> params);  
    
    List<Food> getFoodsByRestaurantId(int resId);
    
    Long countFood(Map<String, String> params);

    Food getFoodById(int id);
    
    boolean addOrUpdateFood(Food f);

    boolean deleteFood(int id);
    
    Double getAverageRateForRestaurant(int resId);
    
    Double getAverageRateForFood(int foodId);
    
    boolean updateFood(Map<String, String> params, MultipartFile avatar);

    Food addFood(Map<String, String> params, MultipartFile image);
}
