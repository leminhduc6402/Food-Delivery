package com.lmd.repository;

import com.lmd.pojo.Cart;

import java.util.List;
import java.util.Map;

public interface ReceiptRepository {
    boolean addReceipt(List<Cart> carts);

}
