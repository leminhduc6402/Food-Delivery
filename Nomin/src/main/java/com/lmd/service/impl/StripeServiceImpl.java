package com.lmd.service.impl;

import com.lmd.pojo.ChargeDTO;
import com.lmd.repository.StripeRepository;
import com.lmd.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {
    @Autowired
    private StripeRepository stripeRepository;
    @Override
    public ChargeDTO charge(String token, Double amount) {
        return this.stripeRepository.charge(token, amount);
    }
}
