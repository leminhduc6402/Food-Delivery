package com.lmd.controller;

import com.lmd.pojo.Cart;
import com.lmd.pojo.ChargeDTO;
import com.lmd.service.ReceiptService;
import com.lmd.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiReceiptController {

    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private StripeService stripeService;

    @PostMapping(path = "/pay/")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void add(@RequestBody List<Cart> carts) {
        this.receiptService.addReceipt(carts);
    }

    @PostMapping("/stripe/")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public ResponseEntity<?> charge(@RequestBody Map<String, String> params) {
        ChargeDTO charge = this.stripeService.charge(params.get("token"), Double.parseDouble(params.get("amount")));
        return new ResponseEntity<>(charge, HttpStatus.OK);
    }

}
