package com.lmd.repository.impl;

import com.lmd.pojo.ChargeDTO;
import com.lmd.repository.StripeRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class StripeRepositoryImpl implements StripeRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;


    @Override
    public ChargeDTO charge(String token, Double amount) {
        try {
            Stripe.apiKey = this.env.getProperty("stripe.api_key");
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", (int) (amount * 100));  // Amount is in cents
            chargeParams.put("currency", "usd");
            chargeParams.put("source", token);  // Obtained via Stripe.js or Checkout
            chargeParams.put("description", "Description here");
            Charge charge = Charge.create(chargeParams);
            return new ChargeDTO(charge);
        } catch (StripeException ex) {
            Logger.getLogger(StripeRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
