/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.controller;

import com.lmd.pojo.CommentsFood;
import com.lmd.pojo.Food;
import com.lmd.pojo.User;
import com.lmd.service.CommentFoodService;
import com.lmd.service.FoodService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:configs.properties")
public class FoodController {

    @Autowired
    private CommentFoodService commentFoodService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private Environment env;

    @RequestMapping("/foods")
    public String foods(Model model, @RequestParam Map<String, String> params) {
        String page = (params.get("page") == null) ? "1" : params.get("page");
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        List<Food> foods = this.foodService.getAllFoods(params); // Lấy danh sách sản phẩm theo điều kiện tìm kiếm
        long count = this.foodService.countFood(params);
        Long totalPage = (long) Math.ceil((double) count * 1.0 / pageSize); // Tính toán lại tổng số trang

        model.addAttribute("foods", foods);
        model.addAttribute("currPage", page);
        model.addAttribute("counter", totalPage);
        return "foods";
    }

    @GetMapping("/detailFood/{id}")
    public String getFoodById(Model model, @PathVariable(value = "id") int id) {
        Food food = this.foodService.getFoodById(id);
        List<CommentsFood> comments = this.commentFoodService.getAllCommentByFoodId(food.getId());
        Double averageRate = this.foodService.getAverageRateForFood(food.getId());
        model.addAttribute("averageRate", averageRate);
        model.addAttribute("comments", comments);
        model.addAttribute("food", food);
        return "detailFood";
    }

    @GetMapping("/detailFood")
    public String list(Model model) {
        model.addAttribute("food", new Food());
        return "detailFood";
    }

    @PostMapping("/addOrUpdateFood")
    public String addOrUpdateFood(@ModelAttribute(value = "food") @Valid Food f, BindingResult rs) {
        Food food = f;
        if (!rs.hasErrors()) {
            if (foodService.addOrUpdateFood(f)) {
                return "redirect:/restaurants/"+ f.restaurantId.getId();
            }
        }
        return "detailFood";
    }
}
