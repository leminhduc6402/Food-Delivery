/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.controller;

import com.lmd.pojo.Restaurant;
import com.lmd.pojo.User;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nome
 */
@Controller
public class UserController {

    @Autowired
    private Environment env;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService resService;

    @GetMapping("/users")
    public String users(Model model, @RequestParam Map<String, String> params) {
        List<User> users = this.userService.getAllUsers(params);
        String page = (params.get("page") == null) ? "1" : params.get("page");

        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        Long count = this.userService.countUser(params);
        Long totalPage = (long) Math.ceil(count * 1.0 / pageSize);

        model.addAttribute("currPage", page);
        model.addAttribute("counter", totalPage);
        model.addAttribute("users", users);
        return "users";
    }
    
    @GetMapping("/users/{id}")
    public String getUserById(Model model, @PathVariable(value = "id") int id) { // id = 1
        User user = this.userService.getUserById(id);
        Restaurant res = user.getRestaurant();
        model.addAttribute("user", user);
        model.addAttribute("restaurant", res);
        return "detail";
    }

    @PostMapping("/addOrUpdateUser")
    public String addOrUpdateUser(@ModelAttribute(value = "user") @Valid User u, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (userService.addOrUpdateUser(u)) {
                return "redirect:/users";
            }
        }
        return "detail";
    }
}
