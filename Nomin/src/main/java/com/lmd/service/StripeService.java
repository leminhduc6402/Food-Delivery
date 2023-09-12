package com.lmd.service;

import com.lmd.pojo.ChargeDTO;

public interface StripeService {
    public ChargeDTO charge(String token, Double amount);
}
