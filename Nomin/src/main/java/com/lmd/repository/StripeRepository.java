package com.lmd.repository;

import com.lmd.pojo.ChargeDTO;

public interface StripeRepository {
    public ChargeDTO charge(String token, Double amount);
}
