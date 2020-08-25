/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.three.bsm.service;

import com.mthree.bsm.entity.Order;
import com.mthree.bsm.entity.OrderStatus;
import static com.mthree.bsm.entity.OrderStatus.CANCELLED;
import static com.mthree.bsm.entity.OrderStatus.EDIT_LOCK;
import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.entity.User;
import com.mthree.bsm.repository.AuditDao;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.MissingEntityException;
import com.mthree.bsm.repository.OrderDao;
import com.mthree.bsm.repository.PartyDao;
import com.mthree.bsm.repository.StockDao;
import com.mthree.bsm.repository.TradeDao;
import com.mthree.bsm.repository.UserDao;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tombarton
 */
public class DataOrderService implements OrderService {
    
    @Autowired
    OrderDao orderDao;
    
    @Autowired
    TradeDao tradeDao;
    
    @Autowired
    StockDao stockDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    PartyDao partyDao;
    
    @Autowired
    AuditDao auditDao;

    @Override
    public List<Order> getOrders() {
        return orderDao.getOrders();
    }

    @Override
    public List<Order> getOrdersBySide(boolean isBuy) {
        return orderDao.getOrdersBySide(isBuy);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderDao.getOrdersByStatus(status);
    }

    @Override
    public List<Order> getOrdersByUserId(int id) throws MissingEntityException {
        return orderDao.getOrdersByUserId(id);
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orderDao.getOrderById(id);
    }

    @Override
    public List<Order> getOrdersByPartyId(int id) throws MissingEntityException {
        return orderDao.getOrdersByPartyId(id);
    }

    // doesn't do any matching 
    @Override
    public Order createOrder(int stockId, int partyId, int userId, boolean isBuy, BigDecimal price, int size) throws InvalidEntityException, IOException {
        Stock stock = stockDao.getStockById(stockId).get();
        User user = userDao.getUserById(userId).get();
        Party party = partyDao.getPartyById(partyId).get();
        LocalDateTime versionTime = LocalDateTime.now();
        
        Order order = new Order(user, party, stock, price, size, isBuy, EDIT_LOCK, versionTime);
        
        order = orderDao.createOrder(order);
        
        auditDao.writeMessage("Add Order: " + order.getId() + " to Repository, userId:  " + userId);
        
        return order;
    }

    @Override
    public void cancelOrder(int orderId, int userId) throws MissingEntityException, InvalidEntityException, IOException {
        Order order = orderDao.getOrderById(orderId).get();
        order.setStatus(CANCELLED);
        
        orderDao.editOrder(order);
        
        auditDao.writeMessage("Cancel Order: " + order.getId() + ", userId:  " + userId);
    }

    @Override
    public Order editOrder(int orderId, BigDecimal price, int size, int userId) throws MissingEntityException, InvalidEntityException, IOException {
        Order order = orderDao.getOrderById(orderId).get();
        order.setPrice(price);
        order.setSize(size);
        
        auditDao.writeMessage("Edit Order: " + order.getId() + ", userId:  " + userId);
        
        orderDao.editOrder(order);
        
        return order;
    }

}
