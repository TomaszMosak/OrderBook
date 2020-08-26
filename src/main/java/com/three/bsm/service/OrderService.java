/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.three.bsm.service;

import com.mthree.bsm.entity.Order;
import com.mthree.bsm.entity.OrderStatus;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.MissingEntityException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author tombarton
 */
public interface OrderService {
    
    List<Order> getOrders();
    
    List<Order>  getOrdersBySide(boolean isBuy);
    
    List<Order> getOrdersByStatus(OrderStatus status);
    
    List<Order> getOrdersByPartyId(int id) throws MissingEntityException;
    
    List<Order> getOrdersByUserId(int id) throws MissingEntityException;
    
    Optional<Order> getOrderById(int id);

    List<Order> getOrderHistoryById(int id) throws MissingEntityException;
    
    Order createOrder(int stockId, int partyId, int userId, boolean isBuy, BigDecimal price, int size) throws InvalidEntityException, MissingEntityException, IOException;
    
    void cancelOrder(int id, int userId) throws MissingEntityException, InvalidEntityException, IOException;
    
    Order editOrder(int orderId, BigDecimal price, int size, int userId) throws MissingEntityException, InvalidEntityException, IOException;
    
    void matchOrders() throws IOException, MissingEntityException, InvalidEntityException;

}
