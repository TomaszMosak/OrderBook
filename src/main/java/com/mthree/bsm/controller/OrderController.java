/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.controller;

import com.mthree.bsm.entity.Order;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tombarton
 */
@RestController
public class OrderController {
    
    @GetMapping("/order/buy")
    public List<Order> displayBuyOrders() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @GetMapping("/order/sell")
    public List<Order> displaySellOrders() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @GetMapping("/order/status/{status}")
    public List<Order> displayOrdersByStatus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @GetMapping("/order/{id}")
    public Order displayOrderById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @GetMapping("/order/user/{id}")
    public List<Order> displayOrdersByUserId(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/order?stock-id={stockId}&is-buy={isBuy}&price={price}&size={size}")
    public Order createOrder(int stockId, boolean isBuy, BigDecimal price, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @PostMapping("/order/cancel/{id}")
    public void cancelOrder(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @PostMapping("/order/edit/{id}?stock-id={stockId}&is-buy={isBuy}&price={price}&size={size}")
    public void editOrder(int stockId, boolean isBuy, BigDecimal price, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
