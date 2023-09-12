/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.controller;

import com.lmd.service.FoodService;
import com.lmd.service.RestaurantService;
import com.lmd.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nome
 */
@Controller
public class IndexController {
    
    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private Environment env;
    
    
//        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
//        long count = this.restaurantService.countProduct();
//        model.addAttribute("counter", Math.ceil(count*1.0/pageSize));
    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
//        String page = (params.get("page") == null) ? "1" : params.get("page");
//        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
//        Long count = this.restaurantService.countRestaurant();
//        
//        model.addAttribute("currPage", page);
//        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
//        model.addAttribute("restaurants", this.restaurantService.getAllRestaurants(params));
        return "index";
    }
    
}
