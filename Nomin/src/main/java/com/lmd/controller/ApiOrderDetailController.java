package com.lmd.controller;

import com.lmd.pojo.OrderDetail;
import com.lmd.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiOrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/orderDetails/{id}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailByOrderId(@PathVariable(value = "id")int id){
        List<OrderDetail> list = this.orderDetailService.getOrderDetailByOrderId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
