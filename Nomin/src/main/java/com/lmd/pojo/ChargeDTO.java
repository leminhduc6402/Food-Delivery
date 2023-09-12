package com.lmd.pojo;

import com.stripe.model.Charge;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeDTO {

    private String id;
    private Long amount;

    public ChargeDTO(Charge charge) {
        this.id = charge.getId();
        this.amount = charge.getAmount();
    }
}