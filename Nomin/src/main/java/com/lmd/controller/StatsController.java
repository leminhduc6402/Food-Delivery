package com.lmd.controller;

import com.lmd.service.RestaurantService;
import com.lmd.service.StatsService;
import com.lmd.pojo.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class StatsController {
    @Autowired
    private StatsService statsService;
    @Autowired
    private RestaurantService restaurantService;
    
    @RequestMapping("/stats/{id}") 
    public String stats(Model model, @PathVariable(value = "id") int id, @RequestParam Map<String, String> params){
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("stats", this.statsService.statsRevenue(params, id));
        return "stats";
    }
}
