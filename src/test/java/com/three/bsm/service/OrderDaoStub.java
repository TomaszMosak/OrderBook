/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.three.bsm.service;

import com.mthree.bsm.entity.Order;
import com.mthree.bsm.entity.OrderStatus;
import static com.mthree.bsm.entity.OrderStatus.ACTIVE;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.MissingEntityException;
import com.mthree.bsm.repository.OrderDao;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author tombarton
 */
public class OrderDaoStub implements OrderDao {
    
    private List<Order> orders;
    private int orderId;
    private int orderHistoryId;
    
    
    @Override
    public List<Order> getOrders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getOrdersBySide(boolean isBuy) {
       List<Order> activeSideOrders = orders;
       activeSideOrders = activeSideOrders.stream().filter(o -> o.isBuy()==(isBuy)).collect(Collectors.toList());
       return activeSideOrders.stream().filter(o -> o.getStatus().equals(ACTIVE)).collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) throws MissingEntityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getOrdersByPartyId(int partyId) throws MissingEntityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order createOrder(Order order) throws InvalidEntityException {
        
    }

    @Override
    public void editOrder(Order order) throws MissingEntityException, InvalidEntityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> deleteOrders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
