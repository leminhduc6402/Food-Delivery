package com.lmd.service;

import com.lmd.pojo.Cart;

import java.util.List;
import java.util.Map;

public interface ReceiptService {
    boolean addReceipt(List<Cart> carts);
}
