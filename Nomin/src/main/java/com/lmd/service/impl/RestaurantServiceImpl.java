    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.service.impl;

import com.lmd.pojo.Restaurant;
import com.lmd.pojo.User;
import com.lmd.repository.RestaurantRepository;
import com.lmd.repository.UserRepository;
import com.lmd.service.RestaurantService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nome
 */
@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    private RestaurantRepository RestaurantRepo;
    @Autowired
    private UserRepository userRepo;
    
    @Override
    public List<Restaurant> getAllRestaurants(Map<String, String> params) {
        return this.RestaurantRepo.getAllRestaurants(params);
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        return this.RestaurantRepo.getRestaurantById(id);
    }

    @Override
    public Long countRestaurant(Map<String, String> params) {
        return this.RestaurantRepo.countRestaurant(params);
    }

    @Override
    public Boolean addOrUpdateRestaurant(Restaurant res) {
        return this.RestaurantRepo.addOrUpdateRestaurant(res);
    }

    @Override
    public Restaurant addRestaurant(Map<String, String> params) {
        Restaurant res = new Restaurant();
        User u = new User();
        res.setName(params.get("resName"));   
        
        Double deliveryFee = Double.parseDouble(params.get("deliveryFee"));
        res.setDeliveryFee(deliveryFee);
        
        res.setStatus(0);
        
        u.setId(Integer.parseInt(params.get("userId")));
        res.setUserId(u);

        this.RestaurantRepo.addRestaurant(res);
        return res;
    }

    @Override
    public Restaurant getRestaurantByUserId(int id) {
        return this.RestaurantRepo.getRestaurantByUserId(id);
    }

    @Override
    public Boolean updateRestaurant(Restaurant restaurant) {

        Restaurant res = this.RestaurantRepo.getRestaurantById(restaurant.getId());
        if (res == null)
        {
            return false;
        }

        res.setName(restaurant.getName());
        res.setDeliveryFee(restaurant.getDeliveryFee());
        res.setStatus(restaurant.getStatus());
        res.setUserId(restaurant.getUserId());
        return this.RestaurantRepo.updateRestaurant(res);
    }
}
