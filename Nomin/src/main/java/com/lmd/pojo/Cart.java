package com.lmd.pojo;

import lombok.Data;

@Data
public class Cart {
    private int id;
    private String name;
    private int quantity;
    private Double price;
    public Restaurant restaurantId;
    public String paymentMethod;
}
