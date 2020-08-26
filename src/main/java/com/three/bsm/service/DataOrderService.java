/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.three.bsm.service;

import com.mthree.bsm.entity.Order;
import com.mthree.bsm.entity.OrderStatus;
import static com.mthree.bsm.entity.OrderStatus.ACTIVE;
import static com.mthree.bsm.entity.OrderStatus.CANCELLED;
import static com.mthree.bsm.entity.OrderStatus.EDIT_LOCK;
import static com.mthree.bsm.entity.OrderStatus.FULFILLED;
import static com.mthree.bsm.entity.OrderStatus.MATCH_LOCK;
import static com.mthree.bsm.entity.OrderStatus.PENDING;
import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.entity.Trade;
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
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tombarton
 */
@Service
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
    public List<Order> getOrderHistoryById(int id) throws MissingEntityException {
        return orderDao.getOrderHistoryById(id);
    }

    @Override
    public List<Order> getOrdersByPartyId(int id) throws MissingEntityException {
        return orderDao.getOrdersByPartyId(id);
    }

    // doesn't do any matching 
    @Override
    public Order createOrder(int stockId, int partyId, int userId, boolean isBuy, BigDecimal price, int size) throws InvalidEntityException, MissingEntityException, IOException {
        Stock stock = stockDao.getStockById(stockId).get();
        User user = userDao.getUserById(userId).get();
        Party party = partyDao.getPartyById(partyId).get();
        LocalDateTime versionTime = LocalDateTime.now();
        
        Order order = new Order(user, party, stock, price, size, isBuy, PENDING, versionTime);
        
        order = orderDao.createOrder(order);
        
        auditDao.writeMessage("Add Order: " + order.getId() + " to Repository, userId:  " + userId);
        
        // sets status
        matchOrder(order);
        
        return order;
    }

    @Override
    public void cancelOrder(int orderId, int userId) throws MissingEntityException, InvalidEntityException, IOException {
        Order order = orderDao.getOrderById(orderId).get();
        
        order.setStatus(EDIT_LOCK);
        orderDao.editOrder(order);
        
        LocalDateTime versionTime = LocalDateTime.now();
        order.setVersionTime(versionTime);
        order.setStatus(CANCELLED);
        
        orderDao.editOrder(order);
        
        auditDao.writeMessage("Cancel Order: " + order.getId() + ", userId:  " + userId);
    }


    @Override
    public Order editOrder(int orderId, BigDecimal price, int size, int userId) throws MissingEntityException, InvalidEntityException, IOException {
        Order order = orderDao.getOrderById(orderId).get();
        order.setStatus(EDIT_LOCK);
        orderDao.editOrder(order);
        
        BigDecimal originalPrice = order.getPrice();

        order.setPrice(price);
        order.setSize(size);
        LocalDateTime versionTime = LocalDateTime.now();
        order.setVersionTime(versionTime);
        
        auditDao.writeMessage("Edit Order: " + order.getId() + ", userId:  " + userId);
        
        orderDao.editOrder(order);
        
        // matches order if price changes
        if(!(originalPrice.equals(price))) {
            // sets status
            matchOrder(order);
        }
        
        return order;
    }

    // assuming all orders of same stock
    // match on startup, takes all active orders
    @Override
    public void matchOrders() throws IOException, MissingEntityException, InvalidEntityException {
        List<Order> buyOrders = getActiveOrders(true);
        List<Order> sellOrders = getActiveOrders(false);
        
        for(Order buyOrder: buyOrders) {
            for(Order sellOrder: sellOrders) {
                checkForMatch(buyOrder, sellOrder);
            }
        } 
    }
    
    

    
    private void matchOrder(Order order) throws IOException, MissingEntityException, InvalidEntityException {
        List<Order> counterSideOrders = getActiveOrders(!order.isBuy());
        
        for(Order cso: counterSideOrders) {
            if(order.isBuy() == true){
                checkForMatch(order, cso);
            } else {
                checkForMatch(cso, order);
            }
        }
    }
    
    private void checkForMatch(Order buyOrder, Order sellOrder) throws IOException, MissingEntityException, InvalidEntityException {
        if(buyOrder.getPrice().compareTo(sellOrder.getPrice()) == 1){
                    LocalDateTime executionTime = LocalDateTime.now();
                    Trade trade = new Trade(buyOrder, sellOrder, executionTime);
                    tradeDao.addTrade(trade);
                    auditDao.writeMessage("Add trade:" + trade.getId() + " to Repository.");
                    
                    editMatchedOrders(buyOrder, trade);
                    editMatchedOrders(sellOrder, trade);
                }
    }
    
    private List<Order> getActiveOrders(boolean isBuy) {
        List<Order> activeSideOrders = orderDao.getOrdersBySide(isBuy);
        return activeSideOrders.stream().filter(o -> o.getStatus().equals(ACTIVE)).collect(Collectors.toList());
    }
    
    // when DB first loaded, setting the version time after a match will affect which price is selected for the trade
    // may be an issue
    private void editMatchedOrders(Order order, Trade trade) throws IOException, MissingEntityException, InvalidEntityException {
        
        // might want outside of private method so executes match lock for both orders first
        order.setStatus(MATCH_LOCK);
        orderDao.editOrder(order);
        
        LocalDateTime versionTime = LocalDateTime.now();
        order.setVersionTime(versionTime);
        
        order.setSize(order.getSize() - trade.getQuantity());
        if(order.getSize() == 0){
            order.setStatus(FULFILLED);
        } else {
            order.setStatus(ACTIVE);
        }
        orderDao.editOrder(order);
        
        auditDao.writeMessage("Edit order: " + order.getId() + ", matched.");
    }
    
}
