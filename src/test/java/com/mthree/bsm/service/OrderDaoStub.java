/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.service;

import com.mthree.bsm.entity.Order;
import com.mthree.bsm.entity.OrderStatus;

import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.MissingEntityException;
import com.mthree.bsm.repository.OrderDao;
import org.springframework.stereotype.Repository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tombarton
 */
@Repository
public class OrderDaoStub implements OrderDao {

    private List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> getOrders() {
        return currentOrders().collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersBySide(boolean isBuy) {
        return currentOrders().filter(order -> order.isBuy() == isBuy)
                              .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return currentOrders().filter(order -> order.getStatus() == status)
                              .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) throws MissingEntityException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getOrdersByPartyId(int partyId) throws MissingEntityException {
        return currentOrders().filter(order -> order.getParty().getId() == partyId)
                              .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return currentOrders().filter(order -> order.getId() == id)
                              .findAny();
    }

    /**
     * Gets the full history of the order with the given ID. If there is no order in the system with the given ID, the
     * returned {@link Optional} will not be present.
     *
     * @param id
     */
    @Override
    public List<Order> getOrderHistoryById(int id) throws MissingEntityException {
        List<Order> orderHistory = orders.stream()
                                         .filter(order -> order.getId() == id)
                                         .sorted(Comparator.comparing(Order::getHistoryId))
                                         .collect(Collectors.toList());

        if (orderHistory.isEmpty()) {
            throw new MissingEntityException();
        }

        return orderHistory;
    }

    @Override
    public Order createOrder(Order order) throws InvalidEntityException {
        int newOrderId = orders.stream()
                               .map(Order::getId)
                               .max(Comparator.naturalOrder())
                               .map(id -> id + 1)
                               .orElse(1);

        int newHistoryId = orders.stream()
                                 .map(Order::getHistoryId)
                                 .max(Comparator.naturalOrder())
                                 .map(id -> id + 1)
                                 .orElse(1);

        int newVersion = orders.stream()
                               .map(Order::getVersion)
                               .max(Comparator.naturalOrder())
                               .map(id -> id + 1)
                               .orElse(1);

        order.setId(newOrderId);
        order.setHistoryId(newHistoryId);
        order.setVersion(newVersion);
        order.setVersionTime(LocalDateTime.now());

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (!violations.isEmpty()) {
            throw new InvalidEntityException(violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList()));
        }

        orders.add(order);

        return order;
    }

    @Override
    public void editOrder(Order order) throws MissingEntityException, InvalidEntityException {
        int newHistoryId = orders.stream()
                                 .map(Order::getHistoryId)
                                 .max(Comparator.naturalOrder())
                                 .map(id -> id + 1)
                                 .orElse(1);

        int newVersion = orders.stream()
                               .map(Order::getVersion)
                               .max(Comparator.naturalOrder())
                               .map(id -> id + 1)
                               .orElse(1);

        order.setHistoryId(newHistoryId)
             .setVersion(newVersion)
             .setVersionTime(LocalDateTime.now());

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (!violations.isEmpty()) {
            throw new InvalidEntityException(violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList()));
        }

        orders.add(order);
    }

    @Override
    public List<Order> deleteOrders() {
        List<Order> oldOrders = new ArrayList<>(orders);
        orders = new ArrayList<>();

        return oldOrders;
    }

    private Stream<Order> currentOrders() {
        return orders.stream()
                     .collect(Collectors.groupingBy(Order::getId))
                     .values()
                     .stream()
                     .map(orderHistory -> orderHistory.stream()
                                                      .max(Comparator.comparing(Order::getHistoryId))
                                                      .orElseThrow());
    }

}
