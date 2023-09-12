/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.lmd.pojo.Food;
import com.lmd.pojo.User;
import com.lmd.repository.FoodRepository;
import com.lmd.service.FoodService;
import com.cloudinary.Cloudinary;
import com.lmd.repository.RestaurantRepository;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nome
 */
@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository FoodRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private RestaurantRepository resRepo;

    @Override
    public List<Food> getAllFoods(Map<String, String> params) {
        return this.FoodRepo.getAllFoods(params);
    }

    @Override
    public Long countFood(Map<String, String> params) {
        return this.FoodRepo.countFood(params);
    }

    @Override
    public Food getFoodById(int id) {
        return this.FoodRepo.getFoodById(id);
    }

    @Override
    public boolean deleteFood(int id) {
        return this.FoodRepo.deleteFood(id);
    }

    @Override
    public List<Food> getFoodsByRestaurantId(int resId) {
        return this.FoodRepo.getFoodsByRestaurantId(resId);
    }

    @Override
    public boolean addOrUpdateFood(Food f) {
        if (f.getFile() != null && !f.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(f.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                f.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                f.setImage("https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png");
            }
        } else {
            f.setImage("https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png");
            Food oldFood = this.FoodRepo.getFoodById(f.getId());
            if (oldFood.getId() != null) {
                f.setImage(oldFood.getImage());
            }
        }
        return this.FoodRepo.addOrUpdateFood(f);
    }

    @Override
    public Double getAverageRateForRestaurant(int resId) {
        return this.FoodRepo.getAverageRateForRestaurant(resId);
    }

    @Override
    public Double getAverageRateForFood(int foodId) {
        return this.FoodRepo.getAverageRateForFood(foodId);
    }

    @Override
    public boolean updateFood(Map<String, String> params, MultipartFile image) {  
        int id = Integer.parseInt(params.get("id"));
        String name = params.get("name");     
        Double price = Double.valueOf(params.get("price"));
        String foodType = params.get("foodType");
        int available = Integer.parseInt(params.get("available"));
        int restaurantId = Integer.parseInt(params.get("restaurantId"));

        Food f = this.FoodRepo.getFoodById(id);
        if (f == null) {
            return false;
        }
//             
        f.setName(name);
        f.setPrice(price);
        f.setFoodType(foodType);
        f.setAvailable(available);
        f.setRestaurantId(this.resRepo.getRestaurantById(restaurantId));
        if (image != null && !image.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                f.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(FoodServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (f.getImage().isEmpty())
                f.setImage("https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png");
        }
       
        return this.FoodRepo.updateFood(f);
    }

    @Override
    public Food addFood(Map<String, String> params, MultipartFile image) {
        String name = params.get("name");
        Double price = Double.valueOf(params.get("price"));
        String foodType = params.get("foodType");
        int available = Integer.parseInt(params.get("available"));
        int restaurantId = Integer.parseInt(params.get("restaurantId"));

        Food f = new Food();

        f.setName(name);
        f.setPrice(price);
        f.setFoodType(foodType);
        f.setAvailable(available);
        f.setRestaurantId(this.resRepo.getRestaurantById(restaurantId));

        if (image != null && !image.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                f.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(FoodServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            f.setImage("https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png");
        }

        this.FoodRepo.addFood(f);
        return f;
    }


}
