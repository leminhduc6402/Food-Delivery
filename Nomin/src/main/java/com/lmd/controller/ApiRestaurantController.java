/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.controller;

import com.lmd.pojo.Restaurant;
import com.lmd.service.FoodService;
import com.lmd.service.RestaurantService;
import java.util.List;
import java.util.Map;
import javax.ejb.PostActivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nome
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ApiRestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private FoodService foodService;

    @RequestMapping("/restaurants/")
    public ResponseEntity<List<Restaurant>> list(@RequestParam Map<String, String> params) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants(params);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PostMapping(path = "/restaurants/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Restaurant> addRes(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) {
        Restaurant res = this.restaurantService.addRestaurant(params);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @RequestMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> restaurant(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.restaurantService.getRestaurantById(id), HttpStatus.OK);
    }

    @GetMapping("/restaurants/{id}/getAverageRateForRestaurant")
    public Double getAverageRateForRestaurant(@PathVariable(value = "id") int id) {
        return this.foodService.getAverageRateForRestaurant(id);
    }

    @GetMapping("/restaurantOwn/{id}")
    public ResponseEntity<Restaurant> getRestaurantByUserId(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.restaurantService.getRestaurantByUserId(id), HttpStatus.OK);
    }

    @PatchMapping(path = "/restaurants/{id}/updateRestaurant",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant res = restaurantService.getRestaurantById(restaurant.getId());
        if (res == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.restaurantService.updateRestaurant(restaurant);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
