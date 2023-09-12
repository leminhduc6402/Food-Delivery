package com.lmd.controller;

import com.lmd.pojo.Orderfood;
import com.lmd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/user/{id}")
    public ResponseEntity<List<Orderfood>> getListOrderFoodByUserId(@PathVariable(value = "id") int id){
        List<Orderfood> list = this.orderService.getOrderByUserId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/orders/restaurant/{id}")
    public ResponseEntity<List<Orderfood>> getListOrderFoodByRestaurantId(@PathVariable(value = "id") int id){
        List<Orderfood> list = this.orderService.getOrderByRestaurantId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
