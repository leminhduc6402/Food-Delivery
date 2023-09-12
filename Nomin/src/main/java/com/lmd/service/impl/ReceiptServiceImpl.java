package com.lmd.service.impl;

import com.lmd.pojo.Cart;
import com.lmd.repository.ReceiptRepository;
import com.lmd.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepo;
    @Override
    public boolean addReceipt(List<Cart>carts) {
        return this.receiptRepo.addReceipt(carts);
    }
}
