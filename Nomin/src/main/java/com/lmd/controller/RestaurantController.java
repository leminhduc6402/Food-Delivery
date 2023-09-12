/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.controller;

import com.lmd.pojo.Restaurant;
import com.lmd.pojo.Food;
import com.lmd.pojo.User;
import com.lmd.service.FoodService;
import com.lmd.service.RestaurantService;
import com.lmd.service.UserService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nome
 */
@Controller
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private Environment env;

    @GetMapping("/restaurants")
    public String restaurants(Model model, @RequestParam Map<String, String> params) {
        String page = (params.get("page") == null) ? "1" : params.get("page");
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

        List<Restaurant> restaurants = this.restaurantService.getAllRestaurants(params);
        Long count = this.restaurantService.countRestaurant(params);
        Long totalPage = (long) Math.ceil(count * 1.0 / pageSize);

        model.addAttribute("currPage", page);
        model.addAttribute("counter", totalPage);
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }
    
    @GetMapping("/restaurants/{id}")
    public String getRestaurantById(Model model, @PathVariable(value = "id") int id){
        Restaurant res = this.restaurantService.getRestaurantById(id);
        List<Food> foods = this.foodService.getFoodsByRestaurantId(res.getId());
        Double averageRate = this.foodService.getAverageRateForRestaurant(res.getId());
        User user = this.userService.getUserById(res.getUserId().getId());
        
        
        model.addAttribute("user", user);
        model.addAttribute("averageRate", averageRate);
        model.addAttribute("restaurant", res);
        model.addAttribute("foods", foods);
        return "detailRes";
    }
    
    @PostMapping("/addOrUpdateRes")
    public String addOrUpdateRes(@ModelAttribute(value = "restaurant") @Valid Restaurant res, BindingResult rs) {
        Restaurant r = res;      
        if (!rs.hasErrors()) {
            if (restaurantService.addOrUpdateRestaurant(res)) {
                return "redirect:/restaurants";
            }
        }
        return "detailRes";
    }
}
