/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lmd.service;

import com.lmd.pojo.Restaurant;
import com.lmd.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nome
 */
public interface RestaurantService {

    List<Restaurant> getAllRestaurants(Map<String, String> params);

    Long countRestaurant(Map<String, String> params);

    Restaurant getRestaurantById(int id);

    Boolean addOrUpdateRestaurant(Restaurant res);

    Restaurant addRestaurant(Map<String, String> params);
    
    Restaurant getRestaurantByUserId(int id);

    Boolean updateRestaurant(Restaurant restaurant);
}
