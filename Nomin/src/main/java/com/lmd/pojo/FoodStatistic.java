package com.lmd.pojo;

import lombok.Data;

@Data
public class FoodStatistic {
    private Integer foodId;
    private String foodName;
    private Integer totalQuantity;
    private Double totalRevenue;
}
